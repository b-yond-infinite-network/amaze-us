package com.amertkara.am.ui;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amertkara.am.service.vo.AccountPayload;

@RequestMapping(value = "/")
public interface AccountController {

	@GetMapping("accounts")
	String accounts(Model model);

	@GetMapping
	String create(Model model);

	@PostMapping
	String create(AccountPayload accountPayload);
}
