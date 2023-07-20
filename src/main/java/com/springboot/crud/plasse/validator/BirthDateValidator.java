package com.springboot.crud.plasse.validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, LocalDate> {

	@Override
	public boolean isValid(final LocalDate valueToValidate, final ConstraintValidatorContext context) {
		return ChronoUnit.YEARS.between( valueToValidate , LocalDate.now()) > 18;
	}
}