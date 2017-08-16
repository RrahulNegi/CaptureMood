package com.moodmicroservice.bean;

public class ResponseBean {

	private String msg;
	private String successCode;
	private String failureCode;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSuccessCode() {
		return successCode;
	}
	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}
	public String getFailureCode() {
		return failureCode;
	}
	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}
	
}
