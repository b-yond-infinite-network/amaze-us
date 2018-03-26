package com.amertkara.udm.service;

import com.amertkara.udm.service.vo.UserPayload;

public interface UserEventConsumer {

	void createUser(UserPayload userPayload);
}
