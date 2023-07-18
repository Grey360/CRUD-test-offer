package com.springboot.crud.plasse.exception;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@AllArgsConstructor
@Jacksonized
public class ApiException {
	
	private final int code;
	private final HttpStatus httpStatus;
	private final Map<String, String> errors;
	private final ZonedDateTime timeStamp;
}
