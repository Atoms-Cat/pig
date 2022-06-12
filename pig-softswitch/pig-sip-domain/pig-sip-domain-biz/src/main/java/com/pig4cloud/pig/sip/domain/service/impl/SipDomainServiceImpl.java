package com.pig4cloud.pig.sip.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.sip.domain.api.entity.SipDomain;
import com.pig4cloud.pig.sip.domain.mapper.SipDomainMapper;
import com.pig4cloud.pig.sip.domain.service.SipDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author th158
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SipDomainServiceImpl extends ServiceImpl<SipDomainMapper, SipDomain> implements SipDomainService {

	/**
	 * 获取根域列表
	 * @param longList
	 * @param stringSet
	 * @return
	 */
	@Override
	public Set<String> getDomianByPid(List<Long> longList, Set<String> stringSet) {
		if (longList != null && longList.size() > 0) {
			QueryWrapper<SipDomain> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().in(SipDomain::getId, longList);
			List<SipDomain> list = list(queryWrapper);
			longList = new ArrayList<>();
			if (list != null && list.size() > 0) {
				for (SipDomain sipDomain : list) {
					if (0L == sipDomain.getPid()) {
						stringSet.add(sipDomain.getDomain());
					} else {
						longList.add(sipDomain.getPid());
					}
				}
				if (longList.size() > 0) {
					getDomianByPid(longList, stringSet);
				}
			}
		}
		return stringSet;
	}
}
