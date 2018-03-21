package com.amertkara.udm.service;

import static com.amertkara.udm.service.exception.ErrorCode.USER_NOT_FOUND;
import static com.amertkara.udm.service.exception.ErrorCode.USER_WITH_SAME_MAIL_ALREADY_EXISTS;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.amertkara.udm.model.entity.User;
import com.amertkara.udm.model.repository.UserRepository;
import com.amertkara.udm.service.exception.ErrorPayload;
import com.amertkara.udm.service.exception.UserAlreadyExistException;
import com.amertkara.udm.service.exception.UserNotFoundException;
import com.amertkara.udm.service.validator.UserValidator;
import com.amertkara.udm.service.vo.UserPayload;
import ma.glasnost.orika.MapperFactory;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserValidator userValidator;
	private final UserRepository userRepository;
	private final MapperFactory mapperFactory;

	@Override
	public Long create(UserPayload userPayload) {
		userValidator.validatePayload(userPayload);

		if (userRepository.existsByEmail(userPayload.getEmail())) {
			throw new UserAlreadyExistException(ErrorPayload.builder().code(USER_WITH_SAME_MAIL_ALREADY_EXISTS).build());
		}

		User user = userRepository.save(mapperFactory.getMapperFacade().map(userPayload, User.class));
		log.debug("User is created id={}", user.getId());
		return user.getId();
	}

	@Override
	public UserPayload get(Long id) {
		userValidator.validateId(id);
		User user = getUser(id);
		log.debug("User exists, returning the user id={}", id);
		return mapperFactory.getMapperFacade().map(user, UserPayload.class);
	}

	@Override
	public UserPayload getByAccountIdentifier(String accountIdentifier) {
		userValidator.validateAccountId(accountIdentifier);
		User user = getUser(accountIdentifier);
		log.debug("User exists, returning the user accountIdentifier={}", accountIdentifier);
		return mapperFactory.getMapperFacade().map(user, UserPayload.class);
	}

	@Override
	public void update(Long id, UserPayload userPayload) {
		userValidator.validateIdAndPayload(id, userPayload);
		User user = getUser(id);
		mapperFactory.getMapperFacade().map(userPayload, user);
		log.debug("Mapped the patch data to the user");
		userRepository.save(user);
		log.debug("User is updated, id={}", id);
	}

	@Override
	public void delete(Long id) {
		userValidator.validateId(id);
		User user = getUser(id);
		userRepository.delete(user);
		log.debug("User is deleted, id={}", id);
	}

	private User getUser(Long id) {
		User user = userRepository.findOne(id);
		if (user == null) {
			throw new UserNotFoundException(ErrorPayload.builder().code(USER_NOT_FOUND).build());
		}
		return user;
	}

	private User getUser(String accountIdentifier) {
		User user = userRepository.readByAccountIdentifier(accountIdentifier);
		if (user == null) {
			throw new UserNotFoundException(ErrorPayload.builder().code(USER_NOT_FOUND).build());
		}
		return user;
	}
}
