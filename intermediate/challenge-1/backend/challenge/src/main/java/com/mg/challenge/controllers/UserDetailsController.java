package com.mg.challenge.controllers;

import javax.security.sasl.AuthenticationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mg.challenge.pojos.User;
import com.mg.challenge.services.MapValidationErrorMap;
import com.mg.challenge.services.UserDetailsService;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class UserDetailsController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MapValidationErrorMap mapValidationErrorMap;

	@PostMapping
	public ResponseEntity<?> login(@Valid @RequestBody LoginData data, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorMap.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
//		log.info("data = " + data);
		try {
			User user = authenticate(data);

			String[] rolesList = null;
			if (user.getRoles() != null) {
				rolesList = new String[user.getRoles().size()];
				for (int i = 0; i < rolesList.length; i++) {
					rolesList[i] = user.getRoles().get(i).getCode();
				}
			}

			return new ResponseEntity<UserResponse>(new UserResponse(user.getUsername(), rolesList), HttpStatus.OK);
		} catch (AuthenticationException | UsernameNotFoundException e) {

			return new ResponseEntity<UnauthorizedResponse>(
					new UnauthorizedResponse("Unauthorized", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
		}
	}

	private User authenticate(LoginData data) throws AuthenticationException {
		String username = data.getUsername() == null ? "" : data.getUsername();
		User user = userDetailsService.loadUserByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("User not found - username: " + username);

		String password = data.getPassword() != null ? data.getPassword().trim() : "";
//		String hashedPassword = passwordEncoder.encode(password);

		if (!passwordEncoder.matches(password, user.getPassword()))
			throw new AuthenticationException("Invalid Credentials");
		return user;
	}
}

@Data
@AllArgsConstructor
class LoginData {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}

@Data
@AllArgsConstructor
class UnauthorizedResponse {
	private String message;
	private Integer code;
}

@Data
@AllArgsConstructor
class UserResponse {
	@NotBlank
	private String username;

	private String[] roles;

}