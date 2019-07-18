package com.kepler.exception;

import org.springframework.http.HttpStatus;

public class ResourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Long resource_id;
	private String error_code;
	private HttpStatus status;

	/* CONSTRUCTORS 
	 * ************ */
    public ResourceException(String message) {
        super(message);
    }
    
    public ResourceException(Long resource_id, String message) {
        super(message);
        
        this.resource_id = resource_id;
    }
    public ResourceException(Long resource_id, String error_code, String message) {
    	super(message);
    	
        this.resource_id = resource_id;
        this.error_code = error_code;
    }
    
    public ResourceException(String error_code, String message) {
    	super(message);
    	
        this.error_code = error_code;
    }
    
    public ResourceException(String error_code, String message, HttpStatus status) {
    	super(message);
    	
        this.error_code = error_code;
        this.status = status;
    }

    /* GETTERS / SETTERS 
     * ***************** */
	public Long getResourceId() {
		return this.resource_id;
	}

	public void setResourceId(Long resource_id) {
		this.resource_id = resource_id;
	}

	public String getErrorCode() {
		return this.error_code;
	}

	public void setErrorCode(String error_code) {
		this.error_code = error_code;
	}    
	
    public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}