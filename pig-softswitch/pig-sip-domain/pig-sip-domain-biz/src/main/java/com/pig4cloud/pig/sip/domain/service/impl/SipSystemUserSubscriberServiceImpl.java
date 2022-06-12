package com.pig4cloud.pig.sip.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.sip.domain.api.entity.SipSystemUserSubscriber;
import com.pig4cloud.pig.sip.domain.mapper.SipSystemUserSubscriberMapper;
import com.pig4cloud.pig.sip.domain.service.SipSystemUserSubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SipSystemUserSubscriberServiceImpl extends ServiceImpl<SipSystemUserSubscriberMapper, SipSystemUserSubscriber> implements SipSystemUserSubscriberService {
}
