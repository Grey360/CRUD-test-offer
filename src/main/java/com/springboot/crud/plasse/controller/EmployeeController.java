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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping( "api/v1/user")
@Api(value = "Set of endpoints for Creating, Retrieving, Updating and Deleting of employees.")
public class EmployeeController {

    private ModelMapper modelMapper = new ModelMapper();
    
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	@TrackExecutionTime
	@TrackLoggerTime
	@ApiOperation(value = "To access all employees")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The request has succeeded"),
			@ApiResponse(code = 500, message = "Internal Server Error") 
	})
	public List<Employee> getEmployees(){
		return employeeService.getEmployees();
	}
	
	@GetMapping ("/findById/{id}")
	@TrackExecutionTime
	@TrackLoggerTime
	@ApiOperation(value = "To get an employee by id")
    public Employee getEmployeeById(@PathVariable Long id){
        return employeeService.findById(id);
    }
	
	
	@GetMapping ("/findByUserName/{userName}")
	@TrackExecutionTime
	@TrackLoggerTime
	@ApiOperation(value = "To get an employee by userName")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The request has succeeded"),
			@ApiResponse(code = 404, message = "User not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") 
	})
	public ResponseEntity<Employee> getEmployeeByUserName(@PathVariable String userName) {
		Optional<Employee> employeeData = employeeService.findByUserName(userName); 
        return employeeData.map(response -> ResponseEntity.ok().body(employeeData.get()))
                .orElseThrow(() -> new UserNotFoundException("The user with userName " + userName + " was not found"));
	}

	@PostMapping("/save")
	@TrackExecutionTime
	@TrackLoggerTime
	@ApiOperation(value = "To create or update an employee by passing an EmployeeDto")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "A new employee has been created successfully"),
			@ApiResponse(code = 202, message = "An employee has been updated successfully"),
			@ApiResponse(code = 400, message = "Failed to register user"),
			@ApiResponse(code = 500, message = "Internal Server Error") 
	})
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody EmployeeDto employeeUpdate) {	
		Employee newEmployee = modelMapper.map(employeeUpdate, Employee.class);	

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate birthDate = LocalDate.parse(employeeUpdate.getBirthDate(), formatter);

		if("France".equalsIgnoreCase(employeeUpdate.getCountry()) && DateUtils.isMajeur(birthDate)) {
			Optional<Employee> employeeData = employeeService.findByUserName(employeeUpdate.getUserName());
			newEmployee.setBirthDate(birthDate);

			if (employeeData.isPresent()) {
				newEmployee.setId(employeeData.get().getId());
				newEmployee = employeeService.saveEmployee(newEmployee);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(newEmployee);
			} else {
				newEmployee = employeeService.saveEmployee(newEmployee);
				return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
			}		
		} else {
			throw new ApiRequestException("Failed to register user with userName " + employeeUpdate.getUserName() +" because it doesn't respect the requirements");
		}
	}
	
	@DeleteMapping("/delete/{userName}")
	@TrackExecutionTime
	@TrackLoggerTime
	@ApiOperation(value = "To delete an employee by userName")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Employee has been deleted successfully"),
			@ApiResponse(code = 404, message = "Employee not found"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
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
