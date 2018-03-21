package com.amertkara.udm.service.validator;

import static com.amertkara.udm.service.exception.ErrorCode.INVALID_USER_DATA;
import static com.amertkara.udm.service.exception.ErrorCode.INVALID_USER_IDENTIFIER;

import javax.validation.Validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.amertkara.udm.service.exception.ErrorPayload;
import com.amertkara.udm.service.exception.InvalidUserDataException;
import com.amertkara.udm.service.exception.InvalidUserIdentifierException;
import com.amertkara.udm.service.vo.UserPayload;

@Slf4j
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator {

	private final Validator validator;

	@Override
	public void validateId(Long id) {
		if (id == null) {
			throw new InvalidUserIdentifierException(ErrorPayload.builder().code(INVALID_USER_IDENTIFIER).build());
		}
		log.debug("User Id is valid id={}", id);
	}

	@Override
	public void validateAccountId(String accountIdentifier) {
		if (accountIdentifier == null) {
			throw new InvalidUserIdentifierException(ErrorPayload.builder().code(INVALID_USER_IDENTIFIER).build());
		}
		log.debug("Account Identifier is valid accountIdentifier={}", accountIdentifier);
	}

	@Override
	public void validatePayload(UserPayload userPayload) {
		if (!validator.validate(userPayload).isEmpty()) {
			// TODO: ideally we should get the constraint violations and build a better error payload
			throw new InvalidUserDataException(ErrorPayload.builder().code(INVALID_USER_DATA).build());
		}
		log.debug("User data is valid user={}", userPayload);
	}

	@Override
	public void validateIdAndPayload(Long id, UserPayload userPayload) {
		validateId(id);
		validatePayload(userPayload);
	}
}
