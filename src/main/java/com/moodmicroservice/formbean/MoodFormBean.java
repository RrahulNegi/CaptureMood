package com.moodmicroservice.formbean;

import java.util.Date;

public class MoodFormBean {
	
private int userId;
	
	private String moodState;
	
	private Date date;
	private String projectName;
	private String emailId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	public String getMoodState() {
		return moodState;
	}
	public void setMoodState(String moodState) {
		this.moodState = moodState;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	

}
