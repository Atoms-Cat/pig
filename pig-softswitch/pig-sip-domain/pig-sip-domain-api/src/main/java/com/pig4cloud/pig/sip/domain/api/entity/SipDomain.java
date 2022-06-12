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
@Schema(description = "sip域")
@TableName("sip_domain")
@EqualsAndHashCode(callSuper = true)
public class SipDomain extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	private Long pid;

	private String domain;

	private String delFlag;
}
