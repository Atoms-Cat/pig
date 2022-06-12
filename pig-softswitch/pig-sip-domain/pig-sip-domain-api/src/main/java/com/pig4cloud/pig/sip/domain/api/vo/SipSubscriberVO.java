package com.pig4cloud.pig.sip.domain.api.vo;

import lombok.Data;

/**
 * @author th158
 */
@Data
public class SipSubscriberVO {

	private Long id;

	private String username;

	private String domain;

	private String password;

	private String url;

	private String contactUri;
}
