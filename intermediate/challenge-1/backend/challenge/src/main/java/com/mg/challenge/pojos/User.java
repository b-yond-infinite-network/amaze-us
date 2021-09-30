package com.mg.challenge.pojos;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "AUTH_USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "F_ID")
	private Integer id;

	@Column(name = "F_USERNAME", unique = true)
	private String username;

	@Column(name = "F_PASSWORD")
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "F_CREATED_AT")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "F_UPDATED_AT")
	private Date updatedAt;

//	@Column(name = "F_FIRSTNAME")
//	private String firstName;
//
//	@Column(name = "F_LASTNAME")
//	private String lastName;
//
//	@Column(name = "F_EMAIL")
//	private String email;
//
//	@Column(name = "F_PHONENUMBER")
//	private String phoneNumber;

	@Builder.Default
	@Column(name = "F_ENABLED")
	private Boolean enabled = true;

	@ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "AUTH_USER_ROLE", joinColumns = @JoinColumn(referencedColumnName = "F_ID"), inverseJoinColumns = @JoinColumn(referencedColumnName = "F_ID"))
	private List<Role> roles;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new GregorianCalendar().getTime();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new GregorianCalendar().getTime();
	}
}
