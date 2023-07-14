package com.springboot.crud.plasse.mapper;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.springboot.crud.plasse.entity.Employee;
import com.springboot.crud.plasse.model.EmployeeDto;

@Component
public class EmployeeDTOMapper implements Function<Employee, EmployeeDto> {

	@Override
	public EmployeeDto apply(Employee employee) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String birthDate = formatter.format(employee.getBirthDate());
		return new EmployeeDto(employee.getId(), employee.getUserName(), birthDate, employee.getCountry(), employee.getPhoneNumber(), employee.getGender());
	}

}
