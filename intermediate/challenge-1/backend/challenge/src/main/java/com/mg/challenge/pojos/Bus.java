package com.mg.challenge.pojos;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_BUS")
public class Bus implements Serializable {
	private static final long serialVersionUID = -8622385544816679356L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "F_BUSID")
	private Integer id;

	@Column(name = "F_CAPACITY")
	private Integer capacity;

	@NotBlank(message = "Model is required")
	@Column(name = "F_MODEL", length = 25)
	private String model;

	@NotBlank(message = "Model is required")
	@Column(name = "F_MAKE", length = 25)
	private String make;

	@Column(name = "F_SSN", insertable = false, updatable = false)
	private String driverSSN;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "F_SSN", referencedColumnName = "F_SSN")
	private Driver associatedDriver;

}