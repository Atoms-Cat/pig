package com.pig4cloud.pig.sip.domain.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author th158
 */
@Data
@Schema(description = "sip分机号")
@TableName("sip_subscriber")
@EqualsAndHashCode(callSuper = true)
public class SipSubscriber extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	private String username;
	private String domain;
	private String password;
	private String belongSystem;
	private String delFlag;

}
