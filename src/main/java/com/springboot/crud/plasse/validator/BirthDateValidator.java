package com.springboot.crud.plasse.validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * validate the birthdate
 * @author ludovic.plasse.ext
 *
 */
public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, String> {
	
	@Override	
	public boolean isValid(final String valueToValidate, final ConstraintValidatorContext context) {
		if(valueToValidate == null) {
			setNewErrorMessage("birthDate should not be null", context);
			return false;
		}
		
		boolean isValid = true;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate birthDate ;
		try {
			birthDate = LocalDate.parse(valueToValidate, formatter);
		} catch ( DateTimeException e) {
			setNewErrorMessage("birthDate should respect format yyyy-MM-dd", context);
			return false;
		}

		boolean isMajeur = ChronoUnit.YEARS.between( birthDate , LocalDate.now()) >= 18;
		if(!isMajeur) {
			setNewErrorMessage("client should be at least 18 years old", context);
			isValid = false;
		}

		LocalDate now = LocalDate.now(ZoneId.systemDefault());
		if(birthDate.isAfter(now)) {
			setNewErrorMessage("birthDate should not be a future date", context);
			isValid = false;
		}

		return isValid;
	}
	
	public static void setNewErrorMessage(String newErrorMessage, ConstraintValidatorContext context) {
	    context.disableDefaultConstraintViolation();
	    context.buildConstraintViolationWithTemplate(newErrorMessage).addConstraintViolation();
	}
}