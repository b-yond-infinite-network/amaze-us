package com.amertkara.udm.service.validator;

import com.amertkara.udm.service.vo.UserPayload;

public interface UserValidator {

	/**
	 * Validates the user identifier
	 *
	 * If the userId is null or blank, it should throw {@link com.amertkara.udm.service.exception.InvalidUserIdentifierException}
	 *
	 * @param id user identifier
	 */
	void validateId(Long id);

	/**
	 * Validates the user's account identifier
	 *
	 * If the accountIdentifier is null or blank, it should throw {@link com.amertkara.udm.service.exception.InvalidUserIdentifierException}
	 *
	 * @param accountIdentifier user's account identifier
	 */
	void validateAccountId(String accountIdentifier);

	/**
	 * Validate the user payload
	 *
	 * If the userPayload is not valid, it should throw {@link com.amertkara.udm.service.exception.InvalidUserDataException}
	 *
	 * @param userPayload user data to be validated
	 */
	void validatePayload(UserPayload userPayload);

	/**
	 * Validates both user identifier and user payload. This method delegates to {@link UserValidator#validateId(Long)}
	 * and {@link UserValidator#validatePayload(UserPayload)} methods
 	 *
	 * @param id user identifier
	 * @param userPayload user data to be validated
	 */
	void validateIdAndPayload(Long id, UserPayload userPayload);
}
