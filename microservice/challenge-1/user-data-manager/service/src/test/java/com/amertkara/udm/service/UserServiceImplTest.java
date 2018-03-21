package com.amertkara.udm.service;

import static com.amertkara.udm.service.exception.ErrorCode.USER_NOT_FOUND;
import static com.amertkara.udm.service.exception.ErrorCode.USER_WITH_SAME_MAIL_ALREADY_EXISTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Condition;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.amertkara.udm.model.entity.User;
import com.amertkara.udm.model.repository.UserRepository;
import com.amertkara.udm.service.exception.UserAlreadyExistException;
import com.amertkara.udm.service.exception.UserNotFoundException;
import com.amertkara.udm.service.validator.UserValidator;
import com.amertkara.udm.service.vo.UserPayload;
import ma.glasnost.orika.MapperFactory;

public class UserServiceImplTest {

	@Mock
	private UserValidator userValidator;
	@Mock
	private UserRepository userRepository;
	@Mock(answer = RETURNS_DEEP_STUBS)
	private MapperFactory mapperFactory;
	private UserService userService;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		userService = new UserServiceImpl(userValidator, userRepository, mapperFactory);
		doNothing().when(userValidator).validateId(anyLong());
		doNothing().when(userValidator).validatePayload(any(UserPayload.class));
		doNothing().when(userValidator).validateIdAndPayload(anyLong(), any(UserPayload.class));
		when(userRepository.save(any(User.class))).thenReturn(mock(User.class));
		when(mapperFactory.getMapperFacade().map(any(UserPayload.class), eq(User.class))).thenReturn(mock(User.class));
	}

	@Test
	public void testCreate_givenUserAlreadyExists_shouldThrowUserAlreadyExistException() {
		UserPayload userPayload = createUserPayload();
		when(userRepository.existsByEmail(userPayload.getEmail())).thenReturn(true);

		assertThatExceptionOfType(UserAlreadyExistException.class)
			.isThrownBy(() -> userService.create(userPayload))
			.is(new Condition<>(e -> e.getErrorPayload().getCode() == USER_WITH_SAME_MAIL_ALREADY_EXISTS, "Expecting a UserAlreadyExistException with errorCode=USER_WITH_SAME_MAIL_ALREADY_EXISTS"));
	}

	@Test
	public void testCreate_givenValidUserData_shouldCallRepository() {
		UserPayload userPayload = createUserPayload();
		when(userRepository.existsByEmail(userPayload.getEmail())).thenReturn(false);

		 userService.create(userPayload);

		 verify(userRepository).save(any(User.class));
	}

	@Test
	public void testGet_givenUserDoesNotExist_shouldThrowUserNotFoundException() {
		when(userRepository.findOne(anyLong())).thenReturn(null);

		assertThatExceptionOfType(UserNotFoundException.class)
			.isThrownBy(() -> userService.get(RandomUtils.nextLong()))
			.is(new Condition<>(e -> e.getErrorPayload().getCode() == USER_NOT_FOUND, "Expecting a UserNotFoundException with errorCode=USER_NOT_FOUND"));
	}

	@Test
	public void testGet_givenUserExist_shouldReturnUserPayload() {
		UserPayload userPayload = createUserPayload();
		when(userRepository.findOne(anyLong())).thenReturn(mock(User.class));
		when(mapperFactory.getMapperFacade().map(any(User.class), eq(UserPayload.class))).thenReturn(userPayload);

		assertThat(userService.get(RandomUtils.nextLong())).isEqualToComparingFieldByField(userPayload);
	}

	@Test
	public void testUpdate_givenUserPayload_shouldUpdateUser() {
		UserPayload userPayload = createUserPayload();
		User user = mock(User.class);
		when(userRepository.findOne(anyLong())).thenReturn(user);
		when(mapperFactory.getMapperFacade().map(eq(userPayload), eq(User.class))).thenReturn(user);
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

		userService.update(RandomUtils.nextLong(), userPayload);

		verify(userRepository).save(userArgumentCaptor.capture());
		assertThat(userArgumentCaptor.getValue()).isEqualTo(user);
	}

	@Test
	public void testDelete_givenExistingUserId_shouldDeleteUser() {
		User user = mock(User.class);
		Long userId = RandomUtils.nextLong();
		when(userRepository.findOne(eq(userId))).thenReturn(user);

		userService.delete(userId);

		verify(userRepository).delete(eq(user));
	}

	public static UserPayload createUserPayload() {
		UserPayload userPayload = new UserPayload();
		userPayload.setName(RandomStringUtils.randomAlphabetic(8));
		userPayload.setEmail(RandomStringUtils.randomAlphabetic(8));
		userPayload.setDescription(RandomStringUtils.randomAlphabetic(8));
		return userPayload;
	}
}
