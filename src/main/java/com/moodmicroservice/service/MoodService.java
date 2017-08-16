package com.moodmicroservice.service;

import java.util.Date;
import java.util.List;

import com.moodmicroservice.bean.EmployeeSubmissionBean;
import com.moodmicroservice.bean.MailBean;
import com.moodmicroservice.bean.MoodBean;
import com.moodmicroservice.bean.MoodReportBean;
import com.moodmicroservice.bean.ResponseBean;
import com.moodmicroservice.bean.UserBean;
import com.moodmicroservice.formbean.MoodFormBean;

public interface MoodService {

	public ResponseBean addMood(MoodFormBean moodFrmBean);

	public MoodReportBean getMoodOnDate(Date now, String projectName, String fromDate, String toDate);
	
	public String addEmployeeSubmission(EmployeeSubmissionBean empBean);



	public List<UserBean> sendMailtoUser();
	
	public String resetUserTablemoodFlag();

	public MailBean getMailDetail(int i);

	public UserBean validateUser(int userId);


}
