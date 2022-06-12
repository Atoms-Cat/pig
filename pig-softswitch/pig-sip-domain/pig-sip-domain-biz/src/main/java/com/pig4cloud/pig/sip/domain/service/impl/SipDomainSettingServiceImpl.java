package com.pig4cloud.pig.sip.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.sip.domain.api.entity.SipDomainSetting;
import com.pig4cloud.pig.sip.domain.api.enums.DomainSettingEnum;
import com.pig4cloud.pig.sip.domain.mapper.SipDomainSettingMapper;
import com.pig4cloud.pig.sip.domain.service.SipDomainSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author th158
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SipDomainSettingServiceImpl extends ServiceImpl<SipDomainSettingMapper, SipDomainSetting> implements SipDomainSettingService {

	/**
	 * @param domainId
	 * @return
	 */
	@Override
	public Map<String, List<SipDomainSetting>> getSettingByDomain(Long domainId) {
		QueryWrapper<SipDomainSetting> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SipDomainSetting::getDomainId, domainId);
		List<SipDomainSetting> list = list(queryWrapper);
		if (list != null && list.size() > 0) {
			return list.stream().collect(Collectors.groupingBy(SipDomainSetting::getSettingKey));
		}
		return null;
	}
	
	/**
	 *
	 * @param domainId
	 * @param domainSettingEnum
	 * @return
	 */
	@Override
	public List<String> getSettingByDomain(Long domainId, DomainSettingEnum domainSettingEnum) {
		Map<String, List<SipDomainSetting>> map = getSettingByDomain(domainId);
		if (map != null) {
			List<SipDomainSetting> list = map.get(domainSettingEnum.getKey());
			if (list != null && list.size() > 0) {
				return list.stream().map(SipDomainSetting::getSettingValue).collect(Collectors.toList());
			}
			return null;
		}
		return null;
	}
}
