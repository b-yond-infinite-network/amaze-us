package com.kepler.exception.general;

public class TechnicalErrorException extends RuntimeException {

	private static final long serialVersionUID = -811807278404114373L;
	
	private Long id;

	/* CONSTRUCTORS 
	 * ************ */
	public TechnicalErrorException() {
        super();
    }

	public TechnicalErrorException(String message) {
        super(message);
    }

	public TechnicalErrorException(Throwable cause) {
        super(cause);
    }
	
    public TechnicalErrorException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public TechnicalErrorException(Long id) {
    	super(id.toString());
    	
        this.id = id;
    }
     
    /* GETTERS / SETTERS 
     * ***************** */
    public Long getId() {
        return this.id;
    }
    public void setId(Long id){
    	this.id = id;
    }
}