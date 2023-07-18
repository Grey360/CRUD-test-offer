package com.springboot.crud.plasse.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "birthDate should respect format yyyy-MM-dd")
    private String birthDate;
	
    @Pattern(regexp = "[a-zA-Z\\\\s']+", message = "country should content only alphabetical characters")
	private String country;
	
	private String phoneNumber;

    private Gender gender;

}
