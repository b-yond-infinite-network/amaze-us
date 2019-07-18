package com.kepler.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER_APPLICATION")
@XmlRootElement(name = "user_application")
public class UserApplication {
    	
	/* Definition of the attribute members composing a role
	 * **************************************************** */
	@Id
	@Column(name = "USER_APPLICATION_ID")
	private Long userApplicationId;
	
	@Column(name="USER_APPLICATION_PROFILE")
	private String userApplicationProfile;

	@OneToMany(mappedBy="userApplication", cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JsonIgnore
	private List<Pioneer> pioneers;
	    
	/* Definition of the constructors 
	 * ****************************** */
	public UserApplication() {
		
		super();
		
	}
	
	public UserApplication(Long userApplicationId, String userApplicationProfile, List<Pioneer> pioneers) {
		
		super();
		
		this.userApplicationId = userApplicationId;
		this.userApplicationProfile = userApplicationProfile;
		this.pioneers = pioneers;
	}

	/* GETTERS / SETTERS
	 * ***************** */	
	public Long getUserApplicationId() {
		return userApplicationId;
	}
	@XmlElement
	public void setUserApplicationId(Long userApplicationId) {
		this.userApplicationId = userApplicationId;
	}

	public String getUserApplicationProfile() {
		return userApplicationProfile;
	}
	@XmlElement
	public void setUserApplicationProfile(String userApplicationProfile) {
		this.userApplicationProfile = userApplicationProfile;
	}

	public List<Pioneer> getPioneers() {
		return pioneers;
	}
	@XmlElement
	public void setPioneers(List<Pioneer> pioneers) {
		this.pioneers = pioneers;
	}

	@Override
	public String toString() {
		return "UserApplication [userApplicationId=" + userApplicationId + ", userApplicationProfile="
				+ userApplicationProfile + ", pioneers=" + pioneers + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pioneers == null) ? 0 : pioneers.hashCode());
		result = prime * result + ((userApplicationId == null) ? 0 : userApplicationId.hashCode());
		result = prime * result + ((userApplicationProfile == null) ? 0 : userApplicationProfile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserApplication other = (UserApplication) obj;
		if (pioneers == null) {
			if (other.pioneers != null)
				return false;
		} else if (!pioneers.equals(other.pioneers))
			return false;
		if (userApplicationId == null) {
			if (other.userApplicationId != null)
				return false;
		} else if (!userApplicationId.equals(other.userApplicationId))
			return false;
		if (userApplicationProfile == null) {
			if (other.userApplicationProfile != null)
				return false;
		} else if (!userApplicationProfile.equals(other.userApplicationProfile))
			return false;
		return true;
	}

	public boolean init() {
		
		this.setUserApplicationProfile(this.getUserApplicationProfile().toLowerCase());
		this.setPioneers(new ArrayList<Pioneer>());
		
		return true;
	}
	
	public void add(Pioneer pioneer) {
		
		if (this.getPioneers() == null) {
			this.setPioneers(new ArrayList<Pioneer>());
		}
		
		this.getPioneers().add(pioneer);
		pioneer.setUserApplication(this);
	}
}
