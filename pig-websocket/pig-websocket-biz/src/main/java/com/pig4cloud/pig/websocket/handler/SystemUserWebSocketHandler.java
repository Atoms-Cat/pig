package com.pig4cloud.pig.websocket.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.PigUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author th158
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class SystemUserWebSocketHandler implements WebSocketMessageBrokerConfigurer {

	private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$",
			Pattern.CASE_INSENSITIVE);

	private final OAuth2AuthorizationService authorizationService;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/system-user")
				// 校验 Authorization Token
				.addInterceptors(new HandshakeInterceptor() {
					@Override
					public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
						// 获取 Token
						ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
						String token = servletServerHttpRequest.getServletRequest().getParameter(HttpHeaders.AUTHORIZATION);
						if (!StringUtils.startsWithIgnoreCase(token, "bearer")) {
							return false;
						}
						Matcher matcher = authorizationPattern.matcher(token);
						if (!matcher.matches()) {
							BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
							throw new OAuth2AuthenticationException(error);
						}
						token = matcher.group("token");
						// 判断token是否有效
						OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
						OAuth2AuthenticatedPrincipal principal = introspect(authorization, attributes);
						if (principal == null) {
							return false;
						}
						attributes.put("user", principal);
						attributes.put("authorization", authorization);
						return true;
					}

					@Override
					public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

					}
				})
				.setHandshakeHandler(new DefaultHandshakeHandler() {
					@Override
					protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
						Object principal = attributes.get("usernamePasswordAuthenticationToken");
						if (principal != null && principal instanceof UsernamePasswordAuthenticationToken) {
							UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
							return usernamePasswordAuthenticationToken;
						}
						return null;
					}
				})
				.setAllowedOriginPatterns("*")
				// 指定使用SockJS协议
				.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic", "/user");
		//这是给sendToUser使⽤,前端订阅需要加上/user
//		registry.setUserDestinationPrefix("/user");
		//客户端订阅路径前缀
		registry.enableStompBrokerRelay("/exchange", "/topic", "/queue", "/amq/queue", "/temp-queue")
				// rabbitmq⾥的虚拟host
				.setVirtualHost("/")
				// rabbitmq 服务地址
				.setRelayHost("192.168.181.130")
				// rabbitmq 的 Stomp 端口
				.setRelayPort(61613)
				.setClientLogin("guest")
				.setClientPasscode("guest")
				.setSystemLogin("guest")
				.setSystemPasscode("guest")
				.setSystemHeartbeatSendInterval(5000)
				.setSystemHeartbeatReceiveInterval(4000)
		;
		//服务端点请求前缀
		registry.setApplicationDestinationPrefixes("/request");
	}

	/**
	 * 通过 OAuth2Authorization 获取 PigUser
	 * @param oldAuthorization
	 * @return
	 */
	private OAuth2AuthenticatedPrincipal introspect(OAuth2Authorization oldAuthorization, Map<String, Object> attributes) {
		Map<String, PigUserDetailsService> userDetailsServiceMap = SpringUtil.getBeansOfType(PigUserDetailsService.class);

		Optional<PigUserDetailsService> optional = userDetailsServiceMap.values().stream()
				.filter(service -> service.support(Objects.requireNonNull(oldAuthorization).getRegisteredClientId(),
						oldAuthorization.getAuthorizationGrantType().getValue()))
				.max(Comparator.comparingInt(Ordered::getOrder));

		UserDetails userDetails = null;
		try {
			Object principal = Objects.requireNonNull(oldAuthorization).getAttributes().get(Principal.class.getName());
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
			attributes.put("usernamePasswordAuthenticationToken", usernamePasswordAuthenticationToken);
			Object tokenPrincipal = usernamePasswordAuthenticationToken.getPrincipal();
			userDetails = optional.get().loadUserByUser((PigUser) tokenPrincipal);
		} catch (UsernameNotFoundException notFoundException) {
			log.warn("用户不不存在 {}", notFoundException.getLocalizedMessage());
			throw notFoundException;
		} catch (Exception ex) {
			log.error("资源服务器 introspect Token error {}", ex.getLocalizedMessage());
		}
		return (PigUser) userDetails;
	}
}