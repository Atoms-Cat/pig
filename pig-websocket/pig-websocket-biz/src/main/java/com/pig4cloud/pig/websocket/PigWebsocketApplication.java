package com.pig4cloud.pig.websocket;

import com.pig4cloud.pig.common.feign.annotation.EnablePigFeignClients;
import com.pig4cloud.pig.common.swagger.annotation.EnablePigDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author th158
 */

@EnablePigDoc
@EnablePigFeignClients
@EnableDiscoveryClient
@EnableScheduling
@SpringBootApplication
public class PigWebsocketApplication {
	public static void main(String[] args) {
		SpringApplication.run(PigWebsocketApplication.class, args);
	}
}
