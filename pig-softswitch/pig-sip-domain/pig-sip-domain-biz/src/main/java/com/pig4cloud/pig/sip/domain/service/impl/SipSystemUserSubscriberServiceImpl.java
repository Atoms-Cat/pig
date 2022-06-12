package com.pig4cloud.pig.sip.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.sip.domain.api.dto.SipSystemUserSubscriberDTO;
import com.pig4cloud.pig.sip.domain.api.entity.SipSystemUserSubscriber;
import com.pig4cloud.pig.sip.domain.api.param.SipSubscriberInfoParam;
import com.pig4cloud.pig.sip.domain.api.param.SipUserSubscriberParam;
import com.pig4cloud.pig.sip.domain.mapper.SipSystemUserSubscriberMapper;
import com.pig4cloud.pig.sip.domain.service.SipSystemUserSubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author th158
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SipSystemUserSubscriberServiceImpl extends ServiceImpl<SipSystemUserSubscriberMapper, SipSystemUserSubscriber> implements SipSystemUserSubscriberService {

	private final SipSystemUserSubscriberMapper sipSystemUserSubscriberMapper;

	/**
	 * 根据用户查询使用的分机号
	 *
	 * @param sipUserSubscriberParam
	 * @return
	 */
	@Override
	public List<SipSystemUserSubscriberDTO> getListByUserId(SipUserSubscriberParam sipUserSubscriberParam) {
		return sipSystemUserSubscriberMapper.getListByUserId(sipUserSubscriberParam);
	}

	/**
	 * 新增使用记录
	 * @param userId
	 * @param subscriberId
	 * @param terminalType
	 * @return
	 */
	@Override
	public Boolean addUserSubscriber(Long userId, Long subscriberId, String terminalType) {
		SipSystemUserSubscriber sipSystemUserSubscriber = new SipSystemUserSubscriber();
		sipSystemUserSubscriber.setUserId(userId);
		sipSystemUserSubscriber.setSubscriberId(subscriberId);
		sipSystemUserSubscriber.setTerminalType(terminalType);
		sipSystemUserSubscriber.setCreateTime(LocalDateTime.now());
		return save(sipSystemUserSubscriber);
	}

	/**
	 * 删除使用记录
	 * @param userId
	 * @param subscriberId
	 * @return
	 */
	@Override
	public Boolean delUserSubscriber(Long userId, Long subscriberId) {
		QueryWrapper<SipSystemUserSubscriber> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SipSystemUserSubscriber::getUserId, userId)
				.eq(SipSystemUserSubscriber::getSubscriberId, subscriberId)
				.eq(SipSystemUserSubscriber::getDelFlag, "0");
		SipSystemUserSubscriber sipSystemUserSubscriber = getOne(queryWrapper);
		if (sipSystemUserSubscriber == null) {
			return false;
		}
		sipSystemUserSubscriber.setDelFlag("1");
		sipSystemUserSubscriber.setUpdateTime(LocalDateTime.now());
		return updateById(sipSystemUserSubscriber);
	}


}
