package com.springboot.crud.plasse.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.springboot.crud.plasse.entity.Employee;
import com.springboot.crud.plasse.model.EmployeeDto;
import com.springboot.crud.plasse.model.Gender;

//@Component
//public class EmployeeDTOMapper implements Function<Employee, EmployeeDto> {
//
//	@Override
//	public EmployeeDto apply(Employee employee) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		String birthDate = formatter.format(employee.getBirthDate());
//		return new EmployeeDto(employee.getId(), employee.getUserName(), birthDate, employee.getCountry(), employee.getPhoneNumber(), employee.getGender());
//	}
//
//}

//@Component
//public class EmployeeDTOMapper implements Function<EmployeeDto, Employee> {
//
//	@Override
//	public Employee apply(EmployeeDto employeeDto) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDate birthDate = LocalDate.parse(employeeDto.getBirthDate(), formatter);
//
//		return  Employee.builder()
//				.userName(employeeDto.getUserName())
//				.birthDate(birthDate)
//				.country(employeeDto.getCountry())
//				.phoneNumber(employeeDto.getPhoneNumber())
//				.gender(Gender.parse(employeeDto.getGender()))
//				.build();
//	}
//
//}
