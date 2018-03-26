package com.amertkara.udm.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amertkara.udm.service.vo.UserPayload;

@RequestMapping(value = "/users")
public interface UserApi {
	String PATH_ID = "id";

	/**
	 * Creates a new user record with the provided user payload
	 *
	 * If the user payload is not valid, it will return a 400 (Bad Request) response
	 * If there is already an existing user with the same email address, it will return a 409 (Conflict) response
	 *
	 * @param userPayload user to be created
	 * @return 201 (Created) response with the location header that points to the newly created user
	 */
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	ResponseEntity<Void> create(UserPayload userPayload);

	/**
	 * Gets a user
	 *
	 * If the user does not exist, it will return a 404 (Not Found) response
	 *
	 * @param id user identifier
	 * @return 200 (OK) with the response body containing the user payload
	 */
	@GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
	ResponseEntity<UserPayload> get(Long id);

	/**
	 * Gets a user by account identifier
	 *
	 * If the user does not exist, it will return a 404 (Not Found) response
	 *
	 * @param accountIdentifier user's account identifier
	 * @return 200 (OK) with the response body containing the user payload
	 */
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	ResponseEntity<UserPayload> getByAccountIdentifier(String accountIdentifier);

	/**
	 * Updates a user
	 *
	 * If the user does not exist, it will return a 404 (Not Found) response
	 * If the user payload is not valid, it will return a 400 (Bad Request) response
	 * If there is already an existing user with the same email address, it will return a 409 (Conflict) response
	 *
	 * @param id user identifier
	 * @param userPayload user update payload
	 * @return 204 (No Content) response
	 */
	@PatchMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
	ResponseEntity<Void> update(Long id, UserPayload userPayload);

	/**
	 * Deletes a user
	 *
	 * If the user does not exist, it will return a 404 (Not Found) response
	 *
	 * @param id user identifier
	 * @return 204 (No Content) response
	 */
	@DeleteMapping("/{id}")
	ResponseEntity<Void> delete(Long id);
}
