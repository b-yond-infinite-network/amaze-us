package com.amertkara.udm.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.amertkara.udm.exception.UDMExceptionHandler;
import com.amertkara.udm.service.ServiceConfig;
import com.amertkara.udm.service.UserService;

@Import({
	ServiceConfig.class
})
@Configuration
public class ApiConfig {

	@Bean
	public UserApi userApi(UserService userService) {
		return new UserApiImpl(userService);
	}

	@Bean
	public UDMExceptionHandler udmExceptionHandler() {
		return new UDMExceptionHandler();
	}
}
