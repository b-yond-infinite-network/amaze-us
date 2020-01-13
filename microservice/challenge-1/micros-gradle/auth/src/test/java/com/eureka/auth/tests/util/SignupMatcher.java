package com.eureka.auth.tests.util;

import com.eureka.common.domain.dto.SignUpRequest;
import org.mockito.ArgumentMatcher;

public class SignupMatcher implements ArgumentMatcher<SignUpRequest> {

    String username;
    String password;

    public SignupMatcher(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean matches(SignUpRequest argument) {
        return argument.getPassword().equals(password) && argument.getUsername().equals(username);
    }
}
