package com.amertkara.udm.service.vo;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class UserPayload {
	@NotBlank
	private String name;
	// TODO: write a custom validator for email validation
	@NotBlank
	private String email;
	private String description;
	private String accountIdentifier;
}
