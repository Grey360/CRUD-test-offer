package com.springboot.crud.plasse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.crud.plasse.entity.Employee;
import com.springboot.crud.plasse.exception.UserNotFoundException;
import com.springboot.crud.plasse.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}
	
	public Optional<Employee> findByUserName(String userName) {
		return employeeRepository.findByUserName(userName);
	}
	
	public Employee findById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
	}

	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	public void deleteByUserName(String userName) {
		employeeRepository.deleteByUserName(userName);
	}
}
