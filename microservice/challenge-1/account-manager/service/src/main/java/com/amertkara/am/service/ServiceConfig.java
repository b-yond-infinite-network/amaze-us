package com.amertkara.am.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.amertkara.am.model.ModelConfig;
import com.amertkara.am.model.repository.AccountRepository;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Import({
	ModelConfig.class
})
@Configuration
@Slf4j
public class ServiceConfig {
	public static final String EVENT_EXCHANGE = "eventExchange";

	@Value("${udm.host:localhost}")
	private String udmHost;
	@Value("${udm.port:8099}")
	private String udmPort;

	@Bean
	public Exchange eventExchange() {
		return new TopicExchange(EVENT_EXCHANGE);
	}

	@Bean
	public RestTemplate udmRestTemplate() {
		log.debug("UDM Host={}", udmHost);
		log.debug("UDM Port={}", udmPort);

		DefaultUriTemplateHandler defaultUriTemplateHandler = new DefaultUriTemplateHandler();
		defaultUriTemplateHandler.setBaseUrl(udmHost + ":" + udmPort);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setUriTemplateHandler(defaultUriTemplateHandler);
		return restTemplate;
	}

	@Bean
	public MessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public UserService userService(RestTemplate udmRestTemplate, MessageConverter messageConverter, RabbitTemplate rabbitTemplate, Exchange eventExchange) {
		rabbitTemplate.setMessageConverter(messageConverter);
		return new UserServiceImpl(udmRestTemplate, rabbitTemplate, eventExchange);
	}

	@Bean
	public AccountService accountService(UserService userService, AccountRepository accountRepository) {
		return new AccountServiceImpl(userService, accountRepository, new DefaultMapperFactory.Builder().build());
	}
}
