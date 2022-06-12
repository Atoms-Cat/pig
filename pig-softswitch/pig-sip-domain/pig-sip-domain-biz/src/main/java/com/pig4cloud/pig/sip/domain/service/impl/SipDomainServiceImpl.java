package com.pig4cloud.pig.sip.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.sip.domain.api.entity.SipDomain;
import com.pig4cloud.pig.sip.domain.mapper.SipDomainMapper;
import com.pig4cloud.pig.sip.domain.service.SipDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author th158
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SipDomainServiceImpl extends ServiceImpl<SipDomainMapper, SipDomain> implements SipDomainService {
}
