package com.pig4cloud.pig.sip.domain.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.sip.domain.api.vo.SipDomainVO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author th158
 */
@RestController
@RequestMapping("/domain")
@RequiredArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SipDomainController {

	@GetMapping(value = {"/info"})
	public R<SipDomainVO> info() {
		String username = SecurityUtils.getUser().getUsername();

		return R.ok();
	}
}
