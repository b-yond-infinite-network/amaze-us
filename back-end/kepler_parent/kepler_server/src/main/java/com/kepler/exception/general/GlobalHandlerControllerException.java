package com.kepler.exception.general;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kepler.exception.ResourceException;

/* Apply the scope of this error on all the files at 'com.renault' */
@ControllerAdvice(basePackages = {"com.renault"})
public class GlobalHandlerControllerException extends ResponseEntityExceptionHandler{

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "customDateBinder", new CustomDateEditor(dateFormat, true));
	}
	
	@ModelAttribute
    public void globalAttributes(Model model) {
		model.addAttribute("technicalError", "Une erreur technique est survenue !");
    }
	
	@ExceptionHandler(TechnicalErrorException.class)
    public ModelAndView technicalErrorException(Exception exception) {
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("exception", exception.getMessage());
	    mav.setViewName("error");
	    return mav;
	}
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BusinessResourceExceptionResponse> unknowError(HttpServletRequest req, Exception ex) {
        BusinessResourceExceptionResponse response = new BusinessResourceExceptionResponse();
        response.setErrorCode("Technical Error");
        response.setErrorMessage(ex.getMessage());
        response.setRequestUrl(req.getRequestURL().toString()); 
        return new ResponseEntity<BusinessResourceExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<BusinessResourceExceptionResponse> resourceNotFound(HttpServletRequest req, ResourceException ex) {
        BusinessResourceExceptionResponse response = new BusinessResourceExceptionResponse();
        response.setStatus(ex.getStatus());
        response.setErrorCode(ex.getErrorCode());
        response.setErrorMessage(ex.getMessage());
        response.setRequestUrl(req.getRequestURL().toString()); 
        return new ResponseEntity<BusinessResourceExceptionResponse>(response, ex.getStatus());
    }
}