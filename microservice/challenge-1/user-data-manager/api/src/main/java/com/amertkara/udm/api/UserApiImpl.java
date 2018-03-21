package com.amertkara.udm.api;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.amertkara.udm.service.UserService;
import com.amertkara.udm.service.vo.UserPayload;

@Slf4j
@RequiredArgsConstructor
public class UserApiImpl implements UserApi {

	private final UserService userService;

	@Override
	public ResponseEntity<Void> create(@RequestBody UserPayload userPayload) {
		log.debug("Processing a create user request userPayload={}", userPayload);
		Long id = userService.create(userPayload);
		log.debug("Processed create user request userPayload={}", userPayload);
		return ResponseEntity.created(buildLocation(id)).build();
	}

	@Override
	public ResponseEntity<UserPayload> get(@PathVariable(PATH_ID) Long id) {
		log.debug("Processing a get user request id={}", id);
		UserPayload userPayload = userService.get(id);
		log.debug("Processed get user request id={}", id);
		return ResponseEntity.ok(userPayload);
	}

	@Override
	public ResponseEntity<UserPayload> getByAccountIdentifier(@RequestParam("accountIdentifier") String accountIdentifier) {
		log.debug("Processing a get user request accountIdentifier={}", accountIdentifier);
		UserPayload userPayload = userService.getByAccountIdentifier(accountIdentifier);
		log.debug("Processed get user request accountIdentifier={}", accountIdentifier);
		return ResponseEntity.ok(userPayload);
	}

	@Override
	public ResponseEntity<Void> update(@PathVariable(PATH_ID) Long id, @RequestBody UserPayload userPayload) {
		log.debug("Processing an update user request id={} userPayload={}", id, userPayload);
		userService.update(id, userPayload);
		log.debug("Processed an update user request id={} userPayload={}", id, userPayload);
		return ResponseEntity.noContent().location(buildLocation(id)).build();
	}

	@Override
	public ResponseEntity<Void> delete(@PathVariable(PATH_ID) Long id) {
		log.debug("Processing a delete user request id={}", id);
		userService.delete(id);
		log.debug("Processed a delete user request id={}", id);
		return ResponseEntity.noContent().build();
	}

	private URI buildLocation(Long id) {
		return UriComponentsBuilder.newInstance().pathSegment("users", String.valueOf(id)).build().toUri();
	}
}
