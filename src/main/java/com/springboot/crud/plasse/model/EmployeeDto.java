package com.springboot.crud.plasse.model;


import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.springboot.crud.plasse.validator.BirthDateConstraint;
import com.springboot.crud.plasse.validator.CountryConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
	
	private Long id;
	
	@NotNull(message = "userName should not be null")
    @Size(min = 3, max = 30, message = "userName should be of 3 - 30 characters")
	private String userName;
	
	@NotNull(message = "birthDate should not be null")
	@BirthDateConstraint(message = "must be at least 18 years old")
	@Past(message = "birthDate must be in the past")
    private LocalDate birthDate;
	
	@NotNull(message = "country should not be null")
	@CountryConstraint(message = "must be french")
	private String country;
	
	private String phoneNumber;

    private Gender gender;

}
