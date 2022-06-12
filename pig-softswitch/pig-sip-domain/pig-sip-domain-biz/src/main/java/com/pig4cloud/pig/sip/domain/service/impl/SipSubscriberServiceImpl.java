package com.pig4cloud.pig.sip.domain.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.core.util.ConstantConfiguration;
import com.pig4cloud.pig.sip.domain.api.dto.SipSystemUserSubscriberDTO;
import com.pig4cloud.pig.sip.domain.api.entity.SipDomain;
import com.pig4cloud.pig.sip.domain.api.entity.SipSubscriber;
import com.pig4cloud.pig.sip.domain.api.enums.DomainSettingEnum;
import com.pig4cloud.pig.sip.domain.api.param.SipSubscriberInfoParam;
import com.pig4cloud.pig.sip.domain.api.param.SipUserSubscriberParam;
import com.pig4cloud.pig.sip.domain.api.vo.SipSubscriberVO;
import com.pig4cloud.pig.sip.domain.mapper.SipSubscriberMapper;
import com.pig4cloud.pig.sip.domain.service.SipDomainSettingService;
import com.pig4cloud.pig.sip.domain.service.SipSubscriberService;
import com.pig4cloud.pig.sip.domain.service.SipSystemUserSubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author th158
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SipSubscriberServiceImpl extends ServiceImpl<SipSubscriberMapper, SipSubscriber> implements SipSubscriberService {

	private final String ASSIGNATION_KEY = "ASSIGNATION:";
	private final RedisTemplate<String, Object> redisTemplate;
	private final SipSubscriberMapper sipSubscriberMapper;
	private final SipSystemUserSubscriberService sipSystemUserSubscriberService;
	private final ConstantConfiguration constantConfiguration;
	private final SipDomainSettingService sipDomainSettingService;
	/**
	 * 分配分机号
	 *
	 * @param sipSubscriberInfoParam
	 * @return
	 */
	@Override
	public List<SipSubscriberVO> assignation(SipSubscriberInfoParam sipSubscriberInfoParam) {
		List<SipSubscriberVO> list = new ArrayList<>();
		try {
			for (SipDomain domain : sipSubscriberInfoParam.getDomainSet()) {
				SipSubscriberVO sipSubscriberVO = new SipSubscriberVO();
				sipSubscriberVO.setContactUri(sipSubscriberInfoParam.getContactUri());
				// TODO
				if ("0".equals(sipSubscriberInfoParam.getTerminalType())) {
					sipSubscriberVO.setUrl(sipDomainSettingService.getSettingByDomain(domain.getId(), DomainSettingEnum.WS_URL));
				} else if ("1".equals(sipSubscriberInfoParam.getTerminalType())) {
					sipSubscriberVO.setUrl(sipDomainSettingService.getSettingByDomain(domain.getId(), DomainSettingEnum.UDP_URL));
				}
				// 获取旧的分配数据
				if (!getUseList(sipSubscriberInfoParam.getUserId(), domain.getDomain(), list, sipSubscriberVO)) {
					// 获取新的分配数据
					setCache(new Page<>(1, 10), domain.getDomain());
					SipSubscriber sipSubscriber = getOneInCache(domain.getDomain());
					if (sipSubscriber != null) {
						BeanUtils.copyProperties(sipSubscriber, sipSubscriberVO);
						// 保存分配使用记录
						sipSystemUserSubscriberService.addUserSubscriber(sipSubscriberInfoParam.getUserId(),
								sipSubscriber.getId(),
								sipSubscriberInfoParam.getTerminalType());
						list.add(sipSubscriberVO);
					}
				}
			}
		} catch (Exception e) {
			log.error("分配分机号异常", e);
			throw new RuntimeException("请稍后再试");
		}
		return list;
	}

	private Boolean getUseList(Long userId, String domain, List<SipSubscriberVO> sipSubscriberVOList, SipSubscriberVO sipSubscriberVO) {
		SipUserSubscriberParam sipUserSubscriberParam = new SipUserSubscriberParam();
		sipUserSubscriberParam.setUserId(userId);
		sipUserSubscriberParam.setDomain(domain);
		List<SipSystemUserSubscriberDTO> list = sipSystemUserSubscriberService.getListByUserId(sipUserSubscriberParam);
		if (list != null && list.size() > 0) {
			for (SipSystemUserSubscriberDTO subscriberDTO : list) {
				BeanUtils.copyProperties(subscriberDTO, sipSubscriberVO);
				sipSubscriberVOList.add(sipSubscriberVO);
			}
			return true;
		}
		return false;
	}

	private void setCache(Page page, String domain) {
		IPage<SipSubscriber> listIPage = sipSubscriberMapper.getNotUseList(page, domain);
		if (listIPage.getRecords() != null) {
			for (SipSubscriber sipSubscriber : listIPage.getRecords()) {
				redisTemplate.opsForSet().add(constantConfiguration.assemble(ASSIGNATION_KEY) + domain, sipSubscriber);
			}
		}
	}

	private SipSubscriber getOneInCache(String domain) {
		return (SipSubscriber) redisTemplate.opsForSet().pop(constantConfiguration.assemble(ASSIGNATION_KEY) + domain);
	}


}
