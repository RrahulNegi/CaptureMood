package com.moodmicroservice.bean;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="employee")
public class Employee {

	private int empId;
	private String name;
	private Date dob;
	private int number;
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
}
