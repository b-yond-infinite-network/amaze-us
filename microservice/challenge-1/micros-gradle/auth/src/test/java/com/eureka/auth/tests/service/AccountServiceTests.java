package com.eureka.auth.tests.service;

import com.eureka.auth.domain.User;
import com.eureka.auth.repository.UserRepository;
import com.eureka.auth.security.Bcrypt;
import com.eureka.auth.service.AccountService;
import com.eureka.common.domain.dto.SignUpRequest;
import com.eureka.common.security.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTests {


    private AccountService accountService;

    private UserRepository userRepository;

    private Bcrypt bcrypt;
    private BCryptPasswordEncoder encoder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userRepository= Mockito.mock(UserRepository.class);
        bcrypt= Mockito.mock(Bcrypt.class);

        encoder=Mockito.mock(BCryptPasswordEncoder.class);

        accountService = new AccountService(userRepository, bcrypt);

        User found = new User();
        found.setUsername("magg");
        found.setId(1L);
        found.setRole(UserRole.USER);
        found.setPassword("123456");

        Mockito.when(userRepository.findOneByUsername(found.getUsername()))
                .thenReturn(Optional.of(found));

        Mockito.when(userRepository.findById(found.getId()))
                .thenReturn(Optional.of(found));

        Mockito.when(userRepository.save(any()))
                .thenReturn(found);

        Mockito.when(bcrypt.passwordEncoder())
                .thenReturn((encoder));

        Mockito.when(bcrypt.passwordEncoder().encode("monterrey10"))
                .thenReturn("123456");



    }


    @Test
    public void testShouldBeFound() {
        String name = "magg";
        boolean flag = accountService.checkExistance(name);
        assertTrue(flag);
    }

    @Test
    public void testNotShouldBeFound() {
        String name = "lol";
        boolean flag = accountService.checkExistance(name);
        assertFalse(flag);
    }

    @Test
    public void testShouldBeFoundUser() {
        String name = "magg";
        Optional<User> user = accountService.find(name);
        assertThat(user.get().getUsername())
                .isEqualTo(name);
    }

    @Test
    public void testNotShouldBeFoundUser() {
        String name = "lol";
        Optional<User> user = accountService.find(name);
        assertThat(user.isEmpty()).isTrue();
    }

    @Test
    public void testShouldBeFoundUserId() {
        Long id = 1L;
        Optional<User> user = accountService.findByID(id);
        assertThat(user.get().getId())
                .isEqualTo(id);
    }

    @Test
    public void testNotShouldBeFoundUserId() {
        Long id = 2L;
        Optional<User> user = accountService.findByID(id);
        assertThat(user.isEmpty()).isTrue();
    }



    @Test
    public void shouldCreateTest() {

        SignUpRequest request = new SignUpRequest();
        request.setUsername("magg");
        request.setPassword("monterrey10");
        Optional<User> user = accountService.create(request);
        assertThat(user.get().getUsername())
                .isEqualTo(request.getUsername());

        assertThat(user.get().getPassword())
                .isEqualTo("123456");
    }
}
