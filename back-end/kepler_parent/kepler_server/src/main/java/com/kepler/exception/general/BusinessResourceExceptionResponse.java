package com.kepler.exception.general;

import org.springframework.http.HttpStatus;

public class BusinessResourceExceptionResponse {
 
    private String error_code;
    private String error_message;
	private String request_url;
	private HttpStatus status;
 
	/* CONSTRUCTORS 
	 * ************ */
    public BusinessResourceExceptionResponse() {
    }
 
    /* GETTERS / SETTERS 
     * ***************** */
    public String getErrorCode() {
        return this.error_code;
    }
    public void setErrorCode(String error_code) {
        this.error_code = error_code;
    }
 
    public String getErrorMessage() {
        return this.error_message;
    }
    public void setErrorMessage(String error_message) {
        this.error_message = error_message;
    }
    
    public String getRequestUrl(){
		return this.request_url;
	}
	public void setRequestUrl(String request_url) {
		this.request_url = request_url;
		
	}
	
    public HttpStatus getStatus() {
		return this.status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}