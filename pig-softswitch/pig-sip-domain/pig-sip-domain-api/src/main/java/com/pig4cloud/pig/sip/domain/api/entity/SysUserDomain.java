package com.pig4cloud.pig.sip.domain.api.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author th158
 */
@Data
@Schema(description = "用户和域的关系")
@TableName("sys_user_domain")
@EqualsAndHashCode(callSuper = true)
public class SysUserDomain extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long userId;

	private long domainId;

}

