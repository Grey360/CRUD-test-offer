package com.springboot.crud.plasse.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.springboot.crud.plasse.model.Gender;

import lombok.Data;

@Entity
@Table(name = "Employee")
@Data
public class Employee {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "user_name")
	private String userName;
	
    @Column(name = "birth_date")
    private LocalDate birthDate;
	
    @Column(name = "country")
	private String country;
	
    @Column(name = "phone_number")
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
    @Column
    private Gender gender;
	
	public Employee id(Long id) {
        this.setId(id);
        return this;
    }
	
	
	public Employee userName(String userName) {
        this.setUserName(userName);
        return this;
    }
	
	public Employee birthDate(LocalDate birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }
	
	public Employee country(String country) {
        this.setCountry(country);
        return this;
    }
	
	public Employee phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }
	
	public Employee gender(Gender gender) {
        this.setGender(gender);
        return this;
    }
}
