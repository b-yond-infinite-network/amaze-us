package com.amertkara.am.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.amertkara.am.model.entity.Account;
import com.amertkara.am.model.repository.AccountRepository;
import com.amertkara.am.service.vo.AccountPayload;
import com.amertkara.am.service.vo.UserPayload;
import ma.glasnost.orika.MapperFactory;

@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final UserService userService;
	private final AccountRepository accountRepository;
	private final MapperFactory mapperFactory;

	@Override
	public void create(AccountPayload accountPayload) {
		UserPayload userPayload = accountPayload.getUser();
		userPayload.setAccountIdentifier(accountPayload.getAccountIdentifier());
		userService.createUser(userPayload);
		accountRepository.saveAndFlush(mapperFactory.getMapperFacade().map(accountPayload, Account.class));
		log.debug("Finished creating account name={} uuid={}", accountPayload.getName(), accountPayload.getAccountIdentifier());
	}

	@Override
	public List<AccountPayload> read() {
		return accountRepository.findAll().stream()
			.map(o -> mapperFactory.getMapperFacade().map(o, AccountPayload.class))
			.map(accountPayload ->  {
				accountPayload.setUser(userService.readUser(accountPayload.getAccountIdentifier()));
				return accountPayload;
			}).collect(Collectors.toList());
	}
}
