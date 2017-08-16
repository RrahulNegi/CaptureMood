package com.moodmicroservice.dao;

import java.util.Date;
import java.util.List;

import com.moodmicroservice.bean.EmployeeSubmissionBean;
import com.moodmicroservice.bean.MailBean;
import com.moodmicroservice.bean.MoodBean;
import com.moodmicroservice.bean.UserBean;

public interface MoodDao {
	
	public String addMood(MoodBean moodBean) throws Exception;
	

	public List<MoodBean> findMoodOnDate(Date now, String projectName, Date fromDate, Date toDate);


	public String addEmployeeSubmission(EmployeeSubmissionBean moodBean);




	public void updateUser(int userId) throws Exception;


	public List<UserBean> getUsertoSendMail();
	
	public MailBean getMailData(int maiId);


	public String resetUserTablemoodFlag();


	public int getUserMoodFlag(int userId);


	public UserBean validateUser(int userId);


	public MoodBean getTodayMood(Date now, String projectName);



}
