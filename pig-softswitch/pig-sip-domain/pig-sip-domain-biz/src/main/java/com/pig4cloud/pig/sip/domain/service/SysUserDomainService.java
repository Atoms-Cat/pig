package com.pig4cloud.pig.sip.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.sip.domain.api.entity.SysUserDomain;

import java.util.Set;

/**
 * @author th158
 */
public interface SysUserDomainService extends IService<SysUserDomain> {

	/**
	 * 根据用户id获取根域列表
	 * @param userId
	 * @return
	 */
	Set<String> getDomainByUserId(Long userId);
}
