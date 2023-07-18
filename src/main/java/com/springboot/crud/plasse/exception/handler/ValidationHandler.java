package com.springboot.crud.plasse.exception.handler;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.crud.plasse.exception.ApiException;

/**
 * validation controller for EmployeeDto's inputs  
 *
 */
@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
		
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		ApiException apiException = new ApiException(400, HttpStatus.BAD_REQUEST, errors, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}
	
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		
//		List<com.springboot.crud.plasse.model.Error> errors = new ArrayList<>();
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//		
//			String fieldName = ((FieldError) error).getField();
//			String message = error.getDefaultMessage();
//			com.springboot.crud.plasse.model.Error err = new com.springboot.crud.plasse.model.Error( message);
//			errors.add(err);
//		});
//		ApiException apiException = new ApiException(400, HttpStatus.BAD_REQUEST, errors, ZonedDateTime.now());
//		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
//	}
}