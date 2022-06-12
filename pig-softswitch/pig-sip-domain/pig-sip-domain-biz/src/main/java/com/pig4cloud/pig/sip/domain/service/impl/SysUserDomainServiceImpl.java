package com.pig4cloud.pig.sip.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.sip.domain.api.entity.SipDomain;
import com.pig4cloud.pig.sip.domain.api.entity.SysUserDomain;
import com.pig4cloud.pig.sip.domain.mapper.SysUserDomainMapper;
import com.pig4cloud.pig.sip.domain.service.SipDomainService;
import com.pig4cloud.pig.sip.domain.service.SysUserDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author th158
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserDomainServiceImpl extends ServiceImpl<SysUserDomainMapper, SysUserDomain> implements SysUserDomainService {

	private final SipDomainService sipDomainService;
	private final SysUserDomainMapper sysUserDomainMapper;

	/**
	 * 根据用户id获取根域列表
	 * @param userId
	 * @return
	 */
	@Override
	public Set<String> getDomainByUserId(Long userId) {
		Set<String> domain = new HashSet<>();
		try {
			List<SipDomain> list = sysUserDomainMapper.getDomainByUserId(userId);
			List<Long> pidList = new ArrayList<>();
			if (list != null) {
				for (SipDomain sipDomain : list) {
					if (0L == sipDomain.getPid()) {
						domain.add(sipDomain.getDomain());
					} else {
						pidList.add(sipDomain.getPid());
					}
				}
			}
			sipDomainService.getDomianByPid(pidList, domain);
		} catch (Exception e) {
			log.error("获取用户的根域异常", e);
			throw new RuntimeException("系统异常，请联系系统管理员");
		}
		return domain;
	}
}
