package com.springboot.crud.plasse.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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



	@ExceptionHandler(value = {ApiRequestException.class, DateTimeParseException.class})
	public ResponseEntity<Object> handleApiRequestException(Exception e, WebRequest request) {	
		Map<String, String> errors = Collections.singletonMap( "message" , e.getMessage());
		
		ApiException apiException = new ApiException(CODE_HTTP_400, BAD_REQUEST, errors, ZonedDateTime.now());
		
		if (e.getCause() != null && e.getCause() instanceof DateTimeException) {
			apiException = new ApiException(CODE_HTTP_400, HttpStatus.BAD_REQUEST, errors, ZonedDateTime.now());
			return new ResponseEntity<>(apiException, BAD_REQUEST);
		}
		

		return new ResponseEntity<>(apiException, BAD_REQUEST);
	}

	@ExceptionHandler(value = {UserNotFoundException.class})
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e, WebRequest request) {	
		Map<String, String> errors = Collections.singletonMap( "message" , e.getMessage());
		
		ApiException apiException = new ApiException(CODE_HTTP_404, HttpStatus.NOT_FOUND, errors, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, NOT_FOUND);
	}
}