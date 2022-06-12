package com.pig4cloud.pig.sip.domain.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.sip.domain.api.entity.SipDomain;
import com.pig4cloud.pig.sip.domain.api.param.SipSubscriberInfoParam;
import com.pig4cloud.pig.sip.domain.api.vo.SipSubscriberVO;
import com.pig4cloud.pig.sip.domain.service.SipSubscriberService;
import com.pig4cloud.pig.sip.domain.service.SysUserDomainService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author th158
 */
@RestController
@RequestMapping("/subscriber")
@RequiredArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SipSubscriberController {

	private final SysUserDomainService sysUserDomainService;
	private final SipSubscriberService sipSubscriberService;

	@GetMapping(value = {"/info"})
	public R<List<SipSubscriberVO>> info(SipSubscriberInfoParam sipSubscriberInfoParam, HttpServletRequest request) {
		PigUser pigUser = SecurityUtils.getUser();
		Set<SipDomain> domainSet = sysUserDomainService.getDomainByUserId(pigUser.getId());
		if (domainSet == null && domainSet.size() == 0) {
			throw new RuntimeException("该用户未配置通话使用域");
		}
		String remoteAddr = ServletUtil.getClientIP(request);
		sipSubscriberInfoParam.setUserId(pigUser.getId());
		sipSubscriberInfoParam.setDomainSet(domainSet);
		sipSubscriberInfoParam.setContactUri(remoteAddr);
		List<SipSubscriberVO> sipSubscriberVO = sipSubscriberService.assignation(sipSubscriberInfoParam);

		return R.ok(sipSubscriberVO);
	}
}
