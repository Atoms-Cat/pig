package com.pig4cloud.pig.sip.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.sip.domain.api.entity.SipSubscriber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author th158
 */
@Mapper
public interface SipSubscriberMapper extends BaseMapper<SipSubscriber> {
	/**
	 * 获取未使用的数据
	 * @param page
	 * @param domain
	 * @return
	 */
	IPage<SipSubscriber> getNotUseList(Page page, @Param("domain") String domain);
}
