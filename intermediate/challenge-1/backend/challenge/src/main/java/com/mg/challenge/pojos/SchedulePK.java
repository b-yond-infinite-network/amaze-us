package com.mg.challenge.pojos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SchedulePK implements Serializable {
	private static final long serialVersionUID = 1393954500614730179L;

	@Column(name = "F_BUSID")
	private Integer busID;
	
	@Column(name = "F_SSN")
	private Integer driverSSN;
}
