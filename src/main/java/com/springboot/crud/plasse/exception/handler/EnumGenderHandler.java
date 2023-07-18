package com.springboot.crud.plasse.exception.handler;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.springboot.crud.plasse.exception.ApiException;


/**
 * note : The deserialisation happens before the validation.
 *
 */
@ControllerAdvice
public class EnumGenderHandler {
	
	
	private static final String ENUM_MSG = "the values accepted for Enum class:";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handleJsonErrors(HttpMessageNotReadableException e){
    	final int code = 400;
    	Map<String, String> errors = Collections.singletonMap("message", e.getCause().getMessage()); 
		
		ApiException apiException = new ApiException(code, HttpStatus.BAD_REQUEST, (errors), ZonedDateTime.now());	
		
		if (e.getCause() != null && e.getCause() instanceof InvalidFormatException) {
			boolean match = e.getCause().getMessage().contains(ENUM_MSG);
			if(match) {
				errors = Collections.singletonMap("message", "values accepted for Enum class:  MALE or FEMALE or UNKNOWN "); 
				apiException = new ApiException(code, HttpStatus.BAD_REQUEST, (errors), ZonedDateTime.now());
				return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
    
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
		Map<String, List<String>> errorResponse = new HashMap<>();
		errorResponse.put("errors", errors);
		return errorResponse;
	}
}