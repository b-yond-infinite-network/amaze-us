package com.cathedral.building.logging;

import org.springframework.stereotype.Component;

@Component
public class CustomExceptions{
	
	private CustomException customException = new CustomException();

	public CustomException getCustomException() {
		return customException;
	}

	public void setCustomException(CustomException customException) {
		this.customException = customException;
	}
	
	
	
	

}
