package com.mg.challenge;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
@Entity
@Table(name = "AUTH_AUTHORITY")
public class Authority implements GrantedAuthority, Cloneable {
	private static final long serialVersionUID = -2971191559942808140L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "F_ID")
	private Integer id;

	@Column(name = "F_CODE")
	private String code;

	@Column(name = "F_DESCRIPTION")
	private String description;

	@Override
	public String getAuthority() {
		return code;
	}

	@Override
	protected Authority clone() throws CloneNotSupportedException {
		return (Authority) super.clone();
	}

}
