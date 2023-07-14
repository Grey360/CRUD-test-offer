package com.springboot.crud.plasse.model;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDto {
	
	private Long id;
	
	@NotNull(message = "userName should not be null")
	@NotEmpty(message = "userName should not be empty")
	private String userName;
	
	@NotNull(message = "birthDate should not be null")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "birthDate should respect format yyyy-MM-dd")
    private String birthDate;
	
    @Pattern(regexp = "[a-zA-Z\\\\s']+", message = "country should content only alphabetical characters")
	private String country;
	
	private String phoneNumber;

    private Gender gender;

}
