package com.amertkara.udm.service;

import javax.validation.Validator;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.amertkara.udm.model.ModelConfig;
import com.amertkara.udm.model.repository.UserRepository;
import com.amertkara.udm.service.validator.UserValidator;
import com.amertkara.udm.service.validator.UserValidatorImpl;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Import({
	ModelConfig.class
})
@Configuration
public class ServiceConfig {
	public static final String EVENT_EXCHANGE = "eventExchange";
	public static final String USER_CREATE_QUEUE = "userCreateQueue";
	public static final String USER_EVENT_DOMAIN = "user.*";

	@Bean
	public UserService userService(UserValidator userValidator, UserRepository userRepository) {
		return new UserServiceImpl(userValidator, userRepository, new DefaultMapperFactory.Builder().build());
	}

	@Bean
	public MessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitListenerContainerFactory rabbitListenerContainerFactory(MessageConverter messageConverter, ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
		simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
		simpleRabbitListenerContainerFactory.setMessageConverter(messageConverter);
		return simpleRabbitListenerContainerFactory;
	}

	@Bean
	public UserEventConsumer eventReceiver(UserService userService) {
		return new UserEventConsumerImpl(userService);
	}

	@Bean
	public UserValidator userValidator(Validator validator) {
		return new UserValidatorImpl(validator);
	}

	@Bean
	public Exchange eventExchange() {
		return new TopicExchange(EVENT_EXCHANGE);
	}

	@Bean
	public Queue queue() {
		return new Queue(USER_CREATE_QUEUE);
	}

	@Bean
	public Binding binding(Queue queue, Exchange eventExchange) {
		return BindingBuilder
			.bind(queue)
			.to(eventExchange)
			.with(USER_EVENT_DOMAIN).noargs();
	}
}
