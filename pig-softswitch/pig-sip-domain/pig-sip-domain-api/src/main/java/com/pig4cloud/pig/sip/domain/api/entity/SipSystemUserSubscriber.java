package com.pig4cloud.pig.sip.domain.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "用户使用的sip分机号记录")
@TableName("sip_system_user_subscriber")
@EqualsAndHashCode(callSuper = true)
public class SipSystemUserSubscriber extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	private Long subscriberId;
	private Long userId;

	/**
	 * 0-正常，1-删除
	 */
	private String delFlag;
	private String terminalType;
}
