package com.springboot.crud.plasse.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.springboot.crud.plasse.validator.BirthDateValidator;

@Constraint(validatedBy = BirthDateValidator.class)
@Target({ TYPE, FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BirthDateConstraint {
	
  String message() default "Invalid birthdate"; 
  
  Class <?> [] groups() default {};
  
  Class <? extends Payload> [] payload() default {};


}
