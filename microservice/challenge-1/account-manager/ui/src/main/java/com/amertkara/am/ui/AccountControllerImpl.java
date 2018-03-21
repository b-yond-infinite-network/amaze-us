package com.amertkara.am.ui;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.amertkara.am.service.AccountService;
import com.amertkara.am.service.vo.AccountPayload;

@Slf4j
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

	private final AccountService accountService;

	@Override
	public String accounts(Model model) {
		model.addAttribute("accounts", accountService.read());
		return "accounts";
	}

	@Override
	public String create(Model model) {
		model.addAttribute("accountPayload", AccountPayload.builder().accountIdentifier(UUID.randomUUID().toString()).build());
		return "create_account";
	}

	@Override
	public String create(@ModelAttribute AccountPayload accountPayload) {
		log.debug("Processing a create user request accountIdentifier={}", accountPayload.getAccountIdentifier());
		accountService.create(accountPayload);
		log.debug("Processed a create user request accountIdentifier={}", accountPayload.getAccountIdentifier());
		return "account_created";
	}
}
