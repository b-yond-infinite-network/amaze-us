package com.eureka.auth.controller;

import com.eureka.auth.domain.User;
import com.eureka.auth.exception.UserNotFoundException;
import com.eureka.common.domain.dto.ApiResponse;
import com.eureka.auth.service.AccountService;
import com.eureka.common.domain.dto.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

/**
 *  AccountController.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@RestController
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/auth/signup", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if(accountService.checkExistance(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        Optional<User> result = accountService.create(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.get().getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping(value = "/auth/{user}")
    @ResponseStatus(HttpStatus.OK)
    User getUser(@PathVariable String user) {
        return accountService.find(user).orElseThrow(UserNotFoundException::new);
    }

    @GetMapping(value = "/auth/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    User getUser(@PathVariable Long userId) {
        return accountService.findByID(userId).orElseThrow(UserNotFoundException::new);
    }
}
