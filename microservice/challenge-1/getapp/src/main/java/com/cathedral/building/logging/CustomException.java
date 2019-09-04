package com.cathedral.building.logging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class CustomException {
	
	@JsonProperty("code")
	String code;
	
	@JsonProperty("type")
	String type;
	
	@JsonProperty("message")
	String message;
	
	public CustomException(){
		
	}
	
	public CustomException(String code, String type, String message){
		super();
		this.code = code;
		this.type = type;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
