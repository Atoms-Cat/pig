package com.pig4cloud.pig.sip.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.sip.domain.api.dto.SipSystemUserSubscriberDTO;
import com.pig4cloud.pig.sip.domain.api.entity.SipSystemUserSubscriber;
import com.pig4cloud.pig.sip.domain.api.param.SipSubscriberInfoParam;
import com.pig4cloud.pig.sip.domain.api.param.SipUserSubscriberParam;

import java.util.List;

/**
 * @author th158
 */
public interface SipSystemUserSubscriberService extends IService<SipSystemUserSubscriber> {

	/**
	 * 根据用户查询使用的分机号
	 * @param sipUserSubscriberParam
	 * @return
	 */
	List<SipSystemUserSubscriberDTO> getListByUserId(SipUserSubscriberParam sipUserSubscriberParam);

	/**
	 * 新增使用记录
	 * @param userId
	 * @param subscriberId
	 * @param terminalType
	 * @return
	 */
	Boolean addUserSubscriber(Long userId, Long subscriberId, String terminalType);

	/**
	 * 删除使用记录
	 * @param userId
	 * @param subscriberId
	 * @return
	 */
	Boolean delUserSubscriber(Long userId, Long subscriberId);
}
