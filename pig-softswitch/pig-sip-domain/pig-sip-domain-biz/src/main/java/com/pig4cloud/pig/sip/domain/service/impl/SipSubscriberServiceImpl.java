package com.pig4cloud.pig.sip.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.sip.domain.api.entity.SipSubscriber;
import com.pig4cloud.pig.sip.domain.mapper.SipSubscriberMapper;
import com.pig4cloud.pig.sip.domain.service.SipSubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SipSubscriberServiceImpl extends ServiceImpl<SipSubscriberMapper, SipSubscriber> implements SipSubscriberService {
}
