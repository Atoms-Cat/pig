package com.pig4cloud.pig.sip.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.sip.domain.api.entity.SipDomain;
import com.pig4cloud.pig.sip.domain.api.entity.SysUserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author th158
 */
@Mapper
public interface SysUserDomainMapper extends BaseMapper<SysUserDomain> {
	/**
	 * 根据用户id获取域信息
	 * @param userId
	 * @return
	 */
	List<SipDomain> getDomainByUserId(@Param("userId") Long userId);
}
