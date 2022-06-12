package com.pig4cloud.pig.sip.domain.api.dto;

import lombok.Data;

/**
 * @author th158
 */
@Data
public class SipSystemUserSubscriberDTO {
	private Long id;
	private String username;
	private String domain;
	private String password;
	private String belongSystem;
	private String terminalType;
}
