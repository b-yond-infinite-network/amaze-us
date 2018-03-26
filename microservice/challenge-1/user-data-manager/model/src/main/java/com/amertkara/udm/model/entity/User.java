package com.amertkara.udm.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 255, nullable = false)
	private String name;

	@Column(length = 255, nullable = false, unique = true)
	private String email;

	@Column(length = 255)
	private String description;

	@Column(length = 64)
	private String accountIdentifier;

}
