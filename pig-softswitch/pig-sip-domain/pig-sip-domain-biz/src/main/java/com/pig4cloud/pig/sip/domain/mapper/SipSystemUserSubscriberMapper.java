package com.pig4cloud.pig.sip.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.sip.domain.api.dto.SipSystemUserSubscriberDTO;
import com.pig4cloud.pig.sip.domain.api.entity.SipSystemUserSubscriber;
import com.pig4cloud.pig.sip.domain.api.param.SipSubscriberInfoParam;
import com.pig4cloud.pig.sip.domain.api.param.SipUserSubscriberParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author th158
 */
@Mapper
public interface SipSystemUserSubscriberMapper extends BaseMapper<SipSystemUserSubscriber> {

	/**
	 * 根据用户查询使用的分机号
	 * @param sipUserSubscriberParam
	 * @return
	 */
	List<SipSystemUserSubscriberDTO> getListByUserId(@Param("param") SipUserSubscriberParam sipUserSubscriberParam);
}
