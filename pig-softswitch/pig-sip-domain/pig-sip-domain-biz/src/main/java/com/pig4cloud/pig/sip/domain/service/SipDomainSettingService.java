package com.pig4cloud.pig.sip.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.sip.domain.api.entity.SipDomainSetting;
import com.pig4cloud.pig.sip.domain.api.enums.DomainSettingEnum;

import java.util.List;
import java.util.Map;

/**
 * @author th158
 */
public interface SipDomainSettingService extends IService<SipDomainSetting> {

	/**
	 *
	 * @param domainId
	 * @return
	 */
	Map<String, List<SipDomainSetting>> getSettingByDomain(Long domainId);

	/**
	 *
	 * @param domainId
	 * @param domainSettingEnum
	 * @return
	 */
	List<String> getSettingByDomain(Long domainId, DomainSettingEnum domainSettingEnum);
}
