package com.springboot.crud.plasse.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.springboot.crud.plasse.exception.ApiException;
import com.springboot.crud.plasse.exception.ApiRequestException;
import com.springboot.crud.plasse.exception.UserNotFoundException;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
public class ApiExceptionHandler {
	
	private static final int CODE_HTTP_400 = 400;
	private static final int CODE_HTTP_404 = 404;
	

	@ExceptionHandler(value = {ApiRequestException.class, InvalidFormatException.class})
	public ResponseEntity<Object> handleApiRequestException(Exception e, WebRequest request) {	
		Map<String, String> errors = Collections.singletonMap( "message" , e.getMessage());
		
		ApiException apiException = new ApiException(CODE_HTTP_400, BAD_REQUEST, errors, LocalDateTime.now());
		
		if (e.getCause() != null && e.getCause() instanceof InvalidFormatException) {		
			errors = Collections.singletonMap( "birthDate" , "birthDate should respect format yyyy-MM-dd");
			apiException = new ApiException(CODE_HTTP_400, HttpStatus.BAD_REQUEST, errors, LocalDateTime.now());
			return new ResponseEntity<>(apiException, BAD_REQUEST);
		}
		

		return new ResponseEntity<>(apiException, BAD_REQUEST);
	}

	@ExceptionHandler(value = {UserNotFoundException.class})
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e, WebRequest request) {	
		Map<String, String> errors = Collections.singletonMap( "message" , e.getMessage());
		
		ApiException apiException = new ApiException(CODE_HTTP_404, HttpStatus.NOT_FOUND, errors, LocalDateTime.now());
		return new ResponseEntity<>(apiException, NOT_FOUND);
	}
}