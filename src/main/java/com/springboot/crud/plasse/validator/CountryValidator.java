package com.springboot.crud.plasse.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CountryValidator implements ConstraintValidator<CountryConstraint, String> {

	@Override
	public boolean isValid(final String valueToValidate, final ConstraintValidatorContext context) {
		return "France".equalsIgnoreCase(valueToValidate);
	}
}