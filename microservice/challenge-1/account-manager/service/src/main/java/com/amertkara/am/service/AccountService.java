package com.amertkara.am.service;

import java.util.List;

import com.amertkara.am.service.vo.AccountPayload;

public interface AccountService {

	void create(AccountPayload accountPayload);

	List<AccountPayload> read();
}
