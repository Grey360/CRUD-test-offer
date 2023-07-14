package com.springboot.crud.plasse.exception;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiException {
	
	//private final String message;
	private final int code;
	private final HttpStatus httpStatus;
	private final Map<String, String> errors;
	private final ZonedDateTime timeStamp;
}
