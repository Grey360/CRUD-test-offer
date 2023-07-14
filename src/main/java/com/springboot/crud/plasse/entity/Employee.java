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
}
