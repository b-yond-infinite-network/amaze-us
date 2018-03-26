package com.amertkara.am.service.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPayload implements Serializable {
	private static final long serialVersionUID = 6143956336871478134L;
	private String name;
	private String email;
	private String description;
	private String accountIdentifier;
}
