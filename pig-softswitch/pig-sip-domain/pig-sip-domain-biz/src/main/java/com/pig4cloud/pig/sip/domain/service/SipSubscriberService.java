package com.pig4cloud.pig.sip.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.sip.domain.api.entity.SipSubscriber;
import com.pig4cloud.pig.sip.domain.api.param.SipSubscriberInfoParam;
import com.pig4cloud.pig.sip.domain.api.vo.SipSubscriberVO;

import java.util.List;

/**
 * @author th158
 */
public interface SipSubscriberService extends IService<SipSubscriber> {

	/**
	 * 分配分机号
	 * @param sipSubscriberInfoParam
	 * @return
	 */
	List<SipSubscriberVO> assignation(SipSubscriberInfoParam sipSubscriberInfoParam);
}
