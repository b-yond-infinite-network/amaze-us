package com.mg.challenge.pojos;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_DRIVER")
public class Driver {
	@Id
	@NotBlank(message = "Social Security Number (SSN) is required")
	@Column(name = "F_SSN", updatable = false, unique = true, length = 50)
	private String ssn;
	
	@NotBlank(message = "First name is required")
	@Column(name = "F_FIRSTNAME", length = 25)
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Column(name = "F_LASTNAME", length = 25)
	private String lastName;

}