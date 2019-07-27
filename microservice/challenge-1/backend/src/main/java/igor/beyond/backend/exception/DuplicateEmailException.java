package igor.beyond.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User with this email already exists")
public class DuplicateEmailException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateEmailException(String errorMessage) {
	    super(errorMessage);
	}

}
