package com.springboot.crud.plasse.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.crud.plasse.IntegrationTest;
import com.springboot.crud.plasse.entity.Employee;
import com.springboot.crud.plasse.exception.UserNotFoundException;
import com.springboot.crud.plasse.mapper.EmployeeDTOMapper;
import com.springboot.crud.plasse.model.EmployeeDto;
import com.springboot.crud.plasse.model.Gender;
import com.springboot.crud.plasse.repository.EmployeeRepository;
import com.springboot.crud.plasse.service.EmployeeService;


/**
 * Integration tests for the EmployeeController REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeControllerIT {

	private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
	private static final String UPDATED_USERNAME = "BBBBBBBBBB";

	private static final LocalDate DEFAULT_BIRTH_DATE= LocalDate.of(1990, 1, 1);
	private static final LocalDate UPDATED_BIRTH_DATE= LocalDate.of(1991, 1, 1);
	
	private static final String DEFAULT_BIRTH_DATE_STR= "1990-01-01";
	private static final String UPDATED_BIRTH_DATE_STR= "1991-01-01";

	private static final Gender DEFAULT_GENDER= Gender.MALE;
	private static final Gender UPDATED_GENDER = Gender.FEMALE;

	private static final String DEFAULT_PHONE_NUMBER = "0600000000";
	private static final String UPDATED_PHONE_NUMBER = "0700000000";

	private static final String DEFAULT_COUNTRY = "France";
	private static final String UPDATED_COUNTRY = "France";

	private static final String ENTITY_API_URL = "/api/v1/user";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	private static Random random = new Random();
	private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeDTOMapper employeeMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restEmployeeMockMvc;

	private Employee employee;
	
    private ModelMapper modelMapper;


	@BeforeEach
	public void initTest() {
		this.employee = createEntity(this.em);
	}

	public static Employee createEntity(EntityManager em) {
		Employee employee = new Employee()
				.userName(DEFAULT_USERNAME)
				.birthDate(DEFAULT_BIRTH_DATE)
				.country(DEFAULT_COUNTRY)
				.phoneNumber(DEFAULT_PHONE_NUMBER)
				.gender(DEFAULT_GENDER);
		return employee;
	}

	@Test
	@Transactional
	void getEmployees() throws Exception {	
		employeeRepository.flush();

		createEmployee();
	
		restEmployeeMockMvc
		.perform(get(ENTITY_API_URL ))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id").value(1L))
		.andExpect(jsonPath("$[0].userName").value(DEFAULT_USERNAME))
		.andExpect(jsonPath("$[0].birthDate").value(DEFAULT_BIRTH_DATE.toString()))
		.andExpect(jsonPath("$[0].country").value(DEFAULT_COUNTRY))
		.andExpect(jsonPath("$[0].phoneNumber").value(DEFAULT_PHONE_NUMBER))
		.andExpect(jsonPath("$[0].gender").value(DEFAULT_GENDER.toString()));			 
	}	 

	@Test
	@Transactional
	void getNonExistingEmployeeByUserName() throws Exception {		 
		MvcResult result  = restEmployeeMockMvc
				.perform(get(ENTITY_API_URL + "/findByUserName/{userName}", "toto"))
				.andDo(print())
				.andExpect(status().is4xxClientError())
				.andReturn();
		Optional<UserNotFoundException> exception = Optional.ofNullable((UserNotFoundException) result.getResolvedException());
		exception.ifPresent( (se) -> assertThat(se, is(notNullValue())));
		exception.ifPresent( (se) -> assertThat(se, is(instanceOf(UserNotFoundException.class))));
	}
	
	@Test
	@Transactional
	void createEmployee() throws Exception {
		employeeRepository.flush();
	
		EmployeeDto employeeDto = new EmployeeDto(1L, DEFAULT_USERNAME, DEFAULT_BIRTH_DATE_STR, DEFAULT_COUNTRY, DEFAULT_PHONE_NUMBER, DEFAULT_GENDER);
		restEmployeeMockMvc
				.perform(post(ENTITY_API_URL + "/save").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDto)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@Transactional
	void updateEmployee() throws Exception {
        employeeRepository.saveAndFlush(employee);
        
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

		EmployeeDto employeeDto = new EmployeeDto(1L, DEFAULT_USERNAME, DEFAULT_BIRTH_DATE_STR, DEFAULT_COUNTRY, DEFAULT_PHONE_NUMBER, DEFAULT_GENDER);
		restEmployeeMockMvc
				.perform(post(ENTITY_API_URL + "/save").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDto)))
				.andExpect(status().isAccepted());

		Optional<Employee> employeeData = employeeService.findByUserName(DEFAULT_USERNAME);
		assertThat(employeeData.get().getUserName()).isEqualTo(DEFAULT_USERNAME);
		
		 // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getUserName()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testEmployee.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEmployee.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEmployee.getGender()).isEqualTo(DEFAULT_GENDER);
	}

}
