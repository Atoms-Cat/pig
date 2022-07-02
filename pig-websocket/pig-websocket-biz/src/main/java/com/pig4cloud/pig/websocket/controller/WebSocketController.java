package com.pig4cloud.pig.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.security.Principal;

/**
 * @author th158
 */
@Slf4j
@RestController
public class WebSocketController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	public AmqpTemplate amqpTemplate;



	/**
	 * 接收用户信息
	 * @param message
	 * @param principal
	 */
	@MessageMapping("/exchange")
	public void getExchangeMessage(@Payload String message, Principal principal) {
		log.info("接收用户信息: {} {}", message, JSONObject.toJSONString(principal));
	}


	/**
	 * 在RabbitMQ 控制台 新建 topic 类型 `atomscat.stomp.socket` 交换器（exchange）, 并且绑定新建的队列（queue）
	 *
	 * @param message
	 * @param principal
	 * @return
	 */
//	@MessageMapping("/exchange")
//	@SendTo("/exchange/atomscat.stomp.socket")
	public String exchange(@Payload String message, Principal principal) {
		return "Hello " + message;
	}

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public String reply(@Payload String message, Principal user) {
		return "Hello " + message;
	}

//	@Scheduled(fixedRate = 1000 * 10)
	public void send() {
		// send all user
		amqpTemplate.convertAndSend("atomscat.stomp.socket","*","ok");
		// send to user token
		amqpTemplate.convertAndSend("atomscat.stomp.socket","pig::admin::dce56ee7-9fe7-43d0-990f-caee35c19f7b", " 123456 ");
		log.info("Scheduled Send");
	}

}
