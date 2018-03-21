package com.amertkara.udm.service;

import static com.amertkara.udm.service.ServiceConfig.USER_CREATE_QUEUE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.amertkara.udm.service.vo.UserPayload;

@Slf4j
@RequiredArgsConstructor
public class UserEventConsumerImpl implements UserEventConsumer {

	private final UserService userService;

	@RabbitListener(queues=USER_CREATE_QUEUE)
	@Override
	public void createUser(UserPayload userPayload) {
		log.debug("Processing user create event");
		userService.create(userPayload);
		log.debug("Processed user create event");
	}
}
