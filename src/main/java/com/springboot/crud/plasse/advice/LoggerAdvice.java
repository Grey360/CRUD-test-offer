package com.springboot.crud.plasse.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggerAdvice {
	
	@Around("@annotation(com.springboot.crud.plasse.advice.annotation.TrackLoggerTime)")
	public Object trackerLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		//java-8-date-time-type-java-time-instant-not-supported-by-default-issue
		ObjectMapper mapper = JsonMapper.builder()
		        .addModule(new JavaTimeModule())
		        .build();

		String methodName = proceedingJoinPoint.getSignature().getName();
		String className = proceedingJoinPoint.getTarget().getClass().toString();
		Object[] args = proceedingJoinPoint.getArgs();
		log.info("The method invoked " + className + " : " + methodName + " with arguments : " + mapper.writeValueAsString(args));
		Object o = proceedingJoinPoint.proceed();
		log.info( className + " : " + methodName + "() with Response : " + mapper.writeValueAsString(o));
		return o;
	}
}
