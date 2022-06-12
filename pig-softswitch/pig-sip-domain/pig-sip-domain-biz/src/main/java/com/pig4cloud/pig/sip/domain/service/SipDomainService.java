package com.pig4cloud.pig.sip.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.sip.domain.api.entity.SipDomain;

import java.util.List;
import java.util.Set;


/**
 * @author th158
 */
public interface SipDomainService extends IService<SipDomain> {

	/**
	 * 获取根域列表
	 * @param longList
	 * @param stringSet
	 * @return
	 */
	Set<String> getDomianByPid(List<Long> longList, Set<String> stringSet);
}
