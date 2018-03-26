package com.amertkara.udm.service;

import com.amertkara.udm.service.vo.UserPayload;

public interface UserService {

	/**
	 * Creates a user
	 *
	 * @param userPayload user to be created
	 * @return user identifier
	 */
	Long create(UserPayload userPayload);

	/**
	 * Gets a user
	 *
	 * @param id user identifier
	 * @return user
	 */
	UserPayload get(Long id);

	/**
	 * Gets a user by account identifier
	 *
	 * @param accountIdentifier user's account identifier
	 * @return user
	 */
	UserPayload getByAccountIdentifier(String accountIdentifier);

	/**
	 * Updates a user
	 *
	 * @param id user identifier
	 * @param userPayload user data to be updated
	 */
	void update(Long id, UserPayload userPayload);

	/**
	 * Deletes a user
	 *
	 * @param id user identifier
	 */
	void delete(Long id);
}
