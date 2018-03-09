package com.challenge.userservice.models;

public class UserModel {
	
	private String name, email, description;
	
	public UserModel(String name, String email, String description) {
		this.name = name;
		this.email = email;
		this.description = description;
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
	
	
	public String toString() {
		return "User: " + String.join(" , ", name, email, description);
	}
	

}
