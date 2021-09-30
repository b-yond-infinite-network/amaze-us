package com.mg.challenge.pojos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "T_DRIVER")
public class Driver implements Serializable {
	private static final long serialVersionUID = -1221832433128856949L;

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