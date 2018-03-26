package com.amertkara.am.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.amertkara.am.service.vo.UserPayload;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private static final String CREATE_USER_ROUTING_KEY = "user.created";

	private final RestTemplate restTemplate;
	private final RabbitTemplate rabbitTemplate;
	private final Exchange exchange;

	@Override
	public void createUser(UserPayload userPayload) {
		log.debug("Creating create user message and sending accountIdentifier={}", userPayload.getAccountIdentifier());
		rabbitTemplate.convertAndSend(exchange.getName(), CREATE_USER_ROUTING_KEY, userPayload);
		log.debug("Created create user message and sending accountIdentifier={}", userPayload.getAccountIdentifier());
	}

	@Override
	public UserPayload readUser(String accountIdentifier) {
		return restTemplate.getForObject(UriComponentsBuilder.fromPath("/users").queryParam("accountIdentifier", accountIdentifier).build().toUriString(), UserPayload.class);
	}
}
