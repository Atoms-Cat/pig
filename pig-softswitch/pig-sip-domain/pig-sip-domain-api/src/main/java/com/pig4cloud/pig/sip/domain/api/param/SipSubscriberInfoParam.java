package com.pig4cloud.pig.sip.domain.api.param;

import lombok.Data;

import java.util.Set;

/**
 * @author th158
 */
@Data
public class SipSubscriberInfoParam {

	/**
	 * 使用终端类型：0：web
	 */
	private String terminalType;

	private Long userId;

	private Set<String> domainSet;

	private String contactUri;
}
