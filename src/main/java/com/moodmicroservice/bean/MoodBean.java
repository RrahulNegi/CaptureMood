package com.moodmicroservice.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection="mood")
public class MoodBean implements Serializable{
	@Id
	private String id;
	private int userId;
	private int happyState;
	private int sadState;
	private int normalState;
	private Date date;
	private String projectName;
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
	public int getHappyState() {
		return happyState;
	}
	public void setHappyState(int happyState) {
		this.happyState = happyState;
	}
	public int getSadState() {
		return sadState;
	}
	public void setSadState(int sadState) {
		this.sadState = sadState;
	}
	public int getNormalState() {
		return normalState;
	}
	public void setNormalState(int normalState) {
		this.normalState = normalState;
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
	
	
	

}
