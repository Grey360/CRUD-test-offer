package com.springboot.crud.plasse.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CountryValidator implements ConstraintValidator<CountryConstraint, String> {

	@Override
	public boolean isValid(final String valueToValidate, final ConstraintValidatorContext context) {
		if(valueToValidate == null) {
			setNewErrorMessage("country should not be null", context);
			return false;
		}
		
		Pattern pattern = Pattern.compile("[a-zA-Z\\\\s']+");
        Matcher matcher = pattern.matcher(valueToValidate);
        if(!matcher.matches()) {
			setNewErrorMessage("country should content only alphabetical characters", context);
			return false;
        }

		if(!"France".equalsIgnoreCase(valueToValidate)) {
        	setNewErrorMessage("must be french", context);
			return false;
		}
		
		return true;
	}
	
	public static void setNewErrorMessage(String newErrorMessage, ConstraintValidatorContext context) {
	    context.disableDefaultConstraintViolation();
	    context.buildConstraintViolationWithTemplate(newErrorMessage).addConstraintViolation();
	}
}