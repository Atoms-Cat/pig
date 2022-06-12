package com.pig4cloud.pig.common.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.util.Optional;

/**
 * @author th158
 */
@AutoConfiguration
public class ConstantConfiguration {

	@Value("${spring.profiles.active}")
	private String active;

	@Value("${spring.application.name}")
	private String name;

	public String assemble(String key) {
		return  name + ":" + active + ":" + key;
	}
}
