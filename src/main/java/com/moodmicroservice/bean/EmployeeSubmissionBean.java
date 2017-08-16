package com.moodmicroservice.bean;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="employeeSubmission")
public class EmployeeSubmissionBean {
	@Id	
	private String id;
	private int userId;
	private int submission;
	private Date date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSubmission() {
		return submission;
	}
	public void setSubmission(int submission) {
		this.submission = submission;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	

}
