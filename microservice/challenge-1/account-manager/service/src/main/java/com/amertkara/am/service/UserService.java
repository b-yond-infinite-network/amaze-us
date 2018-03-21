package com.amertkara.am.service;

import com.amertkara.am.service.vo.UserPayload;

public interface UserService {

	void createUser(UserPayload userPayload);

	UserPayload readUser(String accountIdentifier);
}
