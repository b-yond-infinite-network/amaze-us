package com.cathedral.building.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CathedralBuilding")
public class CathedralBuilding {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="recordkey", unique=true, nullable=false)
    Long recordkey;
	
    @Column(name = "name", nullable=false)
    private String name;
    
    @Column(name = "email", nullable=false)
    private String email;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "createdtms")
    private Timestamp createdtms;

	public Long getRecordkey() {
		return recordkey;
	}

	public void setRecordkey(Long recordkey) {
		this.recordkey = recordkey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedtms() {
		return createdtms;
	}

	public void setCreatedtms(Timestamp createdtms) {
		this.createdtms = createdtms;
	}

    
}
