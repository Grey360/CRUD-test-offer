package com.springboot.crud.plasse.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.crud.plasse.advice.annotation.TrackExecutionTime;
import com.springboot.crud.plasse.advice.annotation.TrackLoggerTime;
import com.springboot.crud.plasse.entity.Employee;
import com.springboot.crud.plasse.exception.ApiRequestException;
import com.springboot.crud.plasse.exception.UserNotFoundException;
import com.springboot.crud.plasse.model.EmployeeDto;
import com.springboot.crud.plasse.service.EmployeeService;
import com.springboot.crud.plasse.utils.DateUtils;


@RestController
@RequestMapping( "api/v1/user")
public class EmployeeController {

    private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	@TrackExecutionTime
	@TrackLoggerTime
    public List<Employee> getEmployees(){
        return employeeService.getEmployees();
    }
	
	@GetMapping ("/findById/{id}")
	@TrackExecutionTime
	@TrackLoggerTime
    public Employee getEmployeeById(@PathVariable Long id){
        return employeeService.findById(id);
    }
	
	@GetMapping ("/findByUserName/{userName}")
	@TrackExecutionTime
	@TrackLoggerTime
	public ResponseEntity<Employee> getEmployeeByUserName(@PathVariable String userName) {
		Optional<Employee> employeeData = employeeService.findByUserName(userName); 
        return employeeData.map(response -> ResponseEntity.ok().body(employeeData.get()))
                .orElseThrow(() -> new UserNotFoundException("The user with userName " + userName + " was not found"));
	}

	@PostMapping("/save")
	@TrackExecutionTime
	@TrackLoggerTime
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDto employeeUpdate) {	
		Employee newEmployee = modelMapper.map(employeeUpdate, Employee.class);	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate birthDate = LocalDate.parse(employeeUpdate.getBirthDate(), formatter);
		
		if("France".equalsIgnoreCase(employeeUpdate.getCountry()) && DateUtils.isMajeur(birthDate)) {
			Optional<Employee> employeeData = employeeService.findByUserName(employeeUpdate.getUserName());
			try {
				newEmployee.setBirthDate(birthDate);

				if (employeeData.isPresent()) {
					newEmployee.setId(employeeData.get().getId());
					newEmployee = employeeService.saveEmployee(newEmployee);
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(newEmployee);
				} else {
					newEmployee = employeeService.saveEmployee(newEmployee);
					return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
				}
			} catch(Exception e ) {
				throw new ApiRequestException("Failed to register user with userName " + employeeUpdate.getUserName(), e);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@DeleteMapping("/delete/{userName}")
	@TrackExecutionTime
	@TrackLoggerTime
	public ResponseEntity<Employee> deleteEmployee(@PathVariable String userName) {
		try {
			Optional<Employee> employeeData = employeeService.findByUserName(userName);
			
			if (employeeData.isPresent()) {
				employeeService.deleteByUserName(userName);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
