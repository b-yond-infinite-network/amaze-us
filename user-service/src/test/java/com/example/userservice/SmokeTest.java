package com.example.userservice;

import com.example.userservice.repository.UserRepository;
import com.example.userservice.web.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SmokeTest {

	@Autowired
	private UserController userController;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void contextLoads() {
		assertThat(userController).isNotNull();
		assertThat(userRepository).isNotNull();
	}

}
