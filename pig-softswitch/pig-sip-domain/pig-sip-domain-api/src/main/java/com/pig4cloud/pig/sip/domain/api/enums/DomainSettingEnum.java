package com.pig4cloud.pig.sip.domain.api.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author th158
 */
@Getter
@RequiredArgsConstructor
public enum DomainSettingEnum {

	WS_URL("WS_URL", "websocket地址")
	;
	/**
	 * 类型
	 */
	private final String key;

	/**
	 * 描述
	 */
	private final String desc;

}
