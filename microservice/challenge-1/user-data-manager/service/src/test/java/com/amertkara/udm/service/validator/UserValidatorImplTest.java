package com.amertkara.udm.service.validator;

import static com.amertkara.udm.service.exception.ErrorCode.INVALID_USER_DATA;
import static com.amertkara.udm.service.exception.ErrorCode.INVALID_USER_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Condition;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.amertkara.udm.service.exception.InvalidUserDataException;
import com.amertkara.udm.service.exception.InvalidUserIdentifierException;
import com.amertkara.udm.service.vo.UserPayload;

public class UserValidatorImplTest {

	@Mock
	private Validator validator;
	private UserValidator userValidator;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		userValidator = new UserValidatorImpl(validator);
	}

	@Test
	public void testValidateId_givenNullId_shouldThrowInvalidUserIdentifierException() throws Exception {
		assertThatExceptionOfType(InvalidUserIdentifierException.class)
			.isThrownBy(() -> userValidator.validateId(null))
			.is(new Condition<>(e -> e.getErrorPayload().getCode() == INVALID_USER_IDENTIFIER, "Expeting a InvalidUserIdentifierException with code=INVALID_USER_IDENTIFIER"));
	}

	@Test
	public void testValidateId_givenValidId_shouldQuitNormally() throws Exception {
		userValidator.validateId(RandomUtils.nextLong());
	}

	@Test
	public void testValidatePayload_givenInvalidPayload_shouldThrowInvalidUserDataException() throws Exception {
		doReturn(new HashSet<>(Arrays.asList(mock(ConstraintViolation.class)))).when(validator).validate(any(UserPayload.class));

		assertThatExceptionOfType(InvalidUserDataException.class)
			.isThrownBy(() -> userValidator.validatePayload(new UserPayload()))
			.is(new Condition<>(e -> e.getErrorPayload().getCode() == INVALID_USER_DATA, "Expeting a InvalidUserDataException with code=INVALID_USER_DATA"));
	}

	@Test
	public void testValidatePayload_givenValidPayload_shouldQuitNormally() throws Exception {
		doReturn(Collections.emptySet()).when(validator).validate(any(UserPayload.class));

		userValidator.validatePayload(new UserPayload());
	}
}
