package com.amertkara.am.ui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.amertkara.am.service.AccountService;
import com.amertkara.am.service.ServiceConfig;

@Import({
	ServiceConfig.class
})
@Configuration
public class UIConfig {

	@Bean
	public AccountController accountController(AccountService accountService) {
		return new AccountControllerImpl(accountService);
	}

}
