package com.amertkara.udm.api;

import static com.amertkara.udm.service.exception.ErrorCode.INVALID_USER_DATA;
import static com.amertkara.udm.service.exception.ErrorCode.USER_NOT_FOUND;
import static com.amertkara.udm.service.exception.ErrorCode.USER_WITH_SAME_MAIL_ALREADY_EXISTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

import com.amertkara.udm.model.entity.User;
import com.amertkara.udm.model.repository.UserRepository;
import com.amertkara.udm.service.vo.UserPayload;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = ApiConfig.class, webEnvironment = MOCK)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@WebAppConfiguration
@EntityScan("com.amertkara.udm.model")
@ActiveProfiles("IT")
public class UserApiImplIntegrationTest extends AbstractTestNGSpringContextTests {
	private static final String CODE_JSON_PATH = "$.code";
	private static final String NAME_JSON_PATH = "$.name";
	private static final String EMAIL_JSON_PATH = "$.email";
	private static final String DESCRIPTION_JSON_PATH = "$.description";
	private static final String PATH_USERS = "/users";

	@Autowired
	private MockMvc mvc;
	@Autowired
	private UserRepository userRepository;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testCreate_givenAlreadyExistingUser_shouldReturnConflict() throws Exception {
		User user = createUserEntity();
		UserPayload userPayload = createUserPayload();
		userRepository.save(user);
		userPayload.setEmail(user.getEmail());

		mvc.perform(post(PATH_USERS).content(objectMapper.writeValueAsString(userPayload))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath(CODE_JSON_PATH, is(USER_WITH_SAME_MAIL_ALREADY_EXISTS.name())))
			.andExpect(status().is(CONFLICT.value()));
	}

	@Test
	public void testCreate_givenInvalidPayloadWithEmptyName_shouldReturnBadRequest() throws Exception {
		UserPayload userPayload = createUserPayload();
		userPayload.setName(null);

		mvc.perform(post(PATH_USERS).content(objectMapper.writeValueAsString(userPayload))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath(CODE_JSON_PATH, is(INVALID_USER_DATA.name())))
			.andExpect(status().is(BAD_REQUEST.value()));
	}

	@Test
	public void testCreate_givenInvalidPayloadWithEmptyEmail_shouldReturnBadRequest() throws Exception {
		UserPayload userPayload = createUserPayload();
		userPayload.setEmail(null);

		mvc.perform(post(PATH_USERS).content(objectMapper.writeValueAsString(userPayload))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath(CODE_JSON_PATH, is(INVALID_USER_DATA.name())))
			.andExpect(status().is(BAD_REQUEST.value()));
	}

	@Test
	public void testCreate_givenValidPayload_shouldCreateUser() throws Exception {
		UserPayload userPayload = createUserPayload();

		mvc.perform(post(PATH_USERS).content(objectMapper.writeValueAsString(userPayload))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(CREATED.value()));

		assertThat(userRepository.existsByEmail(userPayload.getEmail())).isTrue();
	}

	@Test
	public void testGet_givenNonExistingUser_shouldReturnNotFound() throws Exception {
		Long nonExistingUserId = RandomUtils.nextLong();

		mvc.perform(get(PATH_USERS + "/" + nonExistingUserId))
			.andExpect(jsonPath(CODE_JSON_PATH, is(USER_NOT_FOUND.name())))
			.andExpect(status().is(NOT_FOUND.value()));
	}

	@Test
	public void testGet_givenExistingUser_shouldReturnUser() throws Exception {
		User user = createUserEntity();
		user = userRepository.save(user);

		mvc.perform(get(PATH_USERS + "/" + user.getId()))
			.andExpect(jsonPath(NAME_JSON_PATH, is(user.getName())))
			.andExpect(jsonPath(EMAIL_JSON_PATH, is(user.getEmail())))
			.andExpect(jsonPath(DESCRIPTION_JSON_PATH, is(user.getDescription())))
			.andExpect(status().is(OK.value()));
	}

	@Test(description = "Only testing the happy path for update since error cases are already covered in create test cases")
	public void testUpdate_givenNewUserData_shouldUpdate() throws Exception {
		User user = createUserEntity();
		user = userRepository.save(user);
		UserPayload newData = createUserPayload();

		mvc.perform(patch(PATH_USERS + "/" + user.getId())
			.content(objectMapper.writeValueAsString(newData)).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(NO_CONTENT.value()));

		User updatedUser = userRepository.findOne(user.getId());
		assertThat(updatedUser).isEqualToComparingOnlyGivenFields(newData, "name", "email", "description");
	}

	@Test(description = "Only testing the happy path for delete since error cases are already covered in get test cases")
	public void testDelete_givenNewUserData_shouldUpdate() throws Exception {
		User user = createUserEntity();
		user = userRepository.save(user);

		mvc.perform(delete(PATH_USERS + "/" + user.getId()))
			.andExpect(status().is(NO_CONTENT.value()));

		assertThat(userRepository.findOne(user.getId())).isNull();
	}

	private User createUserEntity() {
		User user = new User();
		user.setName(RandomStringUtils.randomAlphabetic(8));
		user.setEmail(RandomStringUtils.randomAlphabetic(8));
		user.setDescription(RandomStringUtils.randomAlphabetic(8));
		return user;
	}

	private UserPayload createUserPayload() {
		UserPayload userPayload = new UserPayload();
		userPayload.setName(RandomStringUtils.randomAlphabetic(8));
		userPayload.setEmail(RandomStringUtils.randomAlphabetic(8));
		userPayload.setDescription(RandomStringUtils.randomAlphabetic(8));
		return userPayload;
	}
}
