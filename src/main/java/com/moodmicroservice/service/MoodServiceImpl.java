package com.moodmicroservice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.moodmicroservice.bean.BarChartData;
import com.moodmicroservice.bean.EmployeeSubmissionBean;
import com.moodmicroservice.bean.MailBean;
import com.moodmicroservice.bean.MoodBean;
import com.moodmicroservice.bean.MoodReportBean;
import com.moodmicroservice.bean.ResponseBean;
import com.moodmicroservice.bean.UserBean;
import com.moodmicroservice.dao.MoodDao;

import com.moodmicroservice.formbean.MoodFormBean;

@Service("moodService")
public class MoodServiceImpl implements MoodService {

	@Autowired
	MoodDao moodDao;

	@Value("${mood.successfull.msg}")
	String successMsg;
	@Value("${mood.exception.msg}")
	String exceptionMsg;
	@Value("${mood.successcode}")
	String successCode;
	@Value("${mood.exceptioncode}")
	String errorCode;
	@Value("${mood.jsonexceptioncode}")
	String jsonCode;
	@Value("${mood.jsonexception.msg}")
	String jsonExceptionMsg;

	@Override
	public ResponseBean addMood(MoodFormBean moodFrmBean) {

		int happyState = 0;
		int sadState = 0;
		int normalState = 0;
		Date now = new Date();
		ResponseBean resBean = new ResponseBean();
		int userId = moodFrmBean.getUserId();
		String projectName = moodFrmBean.getProjectName();
		EmployeeSubmissionBean empSubBean = new EmployeeSubmissionBean();
		empSubBean.setUserId(userId);
		empSubBean.setSubmission(1);
		empSubBean.setDate(now);
		String result = null;
		// String result=addEmployeeSubmission(empSubBean);
		if (null != projectName && userId != 0) {
			MoodBean moodBean = new MoodBean();

			String mood = moodFrmBean.getMoodState();

			moodBean.setDate(now);
			moodBean.setProjectName(moodFrmBean.getProjectName());
			moodBean.setUserId(userId);

			int moodFlag = moodDao.getUserMoodFlag(userId);

			if (moodFlag == 0) {
				MoodBean bdBean = getTodayMood(now, projectName);
				if (bdBean != null) {

					happyState = bdBean.getHappyState();
					sadState = bdBean.getSadState();
					normalState = bdBean.getNormalState();
				}
				if ("happy".equals(mood)) {
					happyState = happyState + 1;

				} else if ("sad".equals(mood)) {
					sadState = sadState + 1;

				} else if ("normal".equals(mood)) {
					normalState = normalState + 1;

				}
				moodBean.setHappyState(happyState);
				moodBean.setSadState(sadState);
				moodBean.setNormalState(normalState);
				moodBean.setDate(now);

				try {
					result = moodDao.addMood(moodBean);
					resBean.setMsg(successMsg);
					resBean.setSuccessCode(successCode);

					// }
					if ("success".equals(result)) {
						// Update User Table
						moodDao.updateUser(userId);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					resBean.setMsg(exceptionMsg);
					resBean.setFailureCode(errorCode);

				}
			} else {
				resBean.setMsg("User already submited ");

			}
		} else {
			resBean.setMsg(jsonExceptionMsg);
			resBean.setFailureCode(jsonCode);
		}

		// String result=moodDao.addMood(moodBean);
		return resBean;

	}

	private MoodBean getTodayMood(Date now, String projectName) {
		return moodDao.getTodayMood(now, projectName);
	}

	@Override
	public MoodReportBean getMoodOnDate(Date now, String projectName, String fromDate, String toDate) {

		Date frmDate = null;
		Date tDate = null;
		try {

			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			frmDate = sd.parse(fromDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(frmDate);
			cal.add(cal.DATE, 1);

			frmDate = cal.getTime();

			tDate = sd.parse(toDate);
			cal.setTime(tDate);
			cal.add(cal.DATE, 1);
			tDate = cal.getTime();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String>chartLabel=new LinkedHashSet<String>();
		List<Integer>greatData=new LinkedList<Integer>();
		List<Integer>goodData=new LinkedList<Integer>();
		List<Integer>sadData=new LinkedList<Integer>();
		Set<String>greatLabel=new LinkedHashSet<String>();
		Set<String>goodLabel=new LinkedHashSet<String>();
		Set<String>sadLabel=new LinkedHashSet<String>();
		MoodReportBean moodReportBean=new MoodReportBean();
		List<MoodBean> moodList = moodDao.findMoodOnDate(now, projectName, frmDate, tDate);
		System.out.println("MOOD LIST SIZE=== "+moodList.size());
		for(MoodBean b:moodList){
			System.out.println("Month :"+b.getDate());
			Date dd=b.getDate();
			SimpleDateFormat ss=new SimpleDateFormat("MMM");
			System.out.println("Month Name "+ss.format(dd));
			chartLabel.add(ss.format(dd));
			greatData.add(b.getHappyState());
			goodData.add(b.getNormalState());
			sadData.add(b.getSadState());
	
		/*	BarChartData barChartData=new  BarChartData();
			barChartData.setData(greatData);
			barChartData.setLabel("Great");
			
			greatData.add(b.getHappyState());
			goodData.add(b.getNormalState());
			sadData.add(b.getSadState());
			greatLabel.add("great");
			goodLabel.add("good");
			sadLabel.add("sad");*/
			System.out.println("Label : Happy"+b.getHappyState());
			System.out.println("Label : Normal"+b.getNormalState());
			System.out.println("Label : Sad"+b.getSadState());
			
			
		}
		
		System.out.println("ChartLabels :"+chartLabel);
		moodReportBean.setChartLabels(chartLabel);
		moodReportBean.setGreatData(greatData);
		moodReportBean.setGoodData(goodData);
		moodReportBean.setSadData(sadData);
		System.out.println("great :"+greatData+" Label :"+greatLabel);
		System.out.println("good :"+goodData+" Label :"+goodLabel);
		System.out.println("sad :"+sadData+" Label :"+sadLabel);
		moodReportBean.setChartLabels(chartLabel);
		BarChartData grBarChartData=new  BarChartData();
		grBarChartData.setData(greatData);
		grBarChartData.setLabel("Great");
		
		BarChartData gdBarChartData=new  BarChartData();
		gdBarChartData.setData(goodData);
		gdBarChartData.setLabel("Good");
		
		BarChartData sdBarChartData=new  BarChartData();
		sdBarChartData.setData(sadData);
		sdBarChartData.setLabel("Sad");
		
		List<BarChartData> list=new ArrayList<BarChartData>();
		list.add(grBarChartData);
		list.add(gdBarChartData);
		list.add(sdBarChartData);
		//moodReportBean.setBarChartData(list);

		return moodReportBean;
	}
	
	public String addEmployeeSubmission(EmployeeSubmissionBean empBean) {
		String result = moodDao.addEmployeeSubmission(empBean);
		return result;

	}

	@Override
	public List<UserBean> sendMailtoUser() {
		List<UserBean> userList = moodDao.getUsertoSendMail();
		return userList;

	}

	@Override
	public String resetUserTablemoodFlag() {
		return moodDao.resetUserTablemoodFlag();
	}

	@Override
	public MailBean getMailDetail(int i) {
		return moodDao.getMailData(i);
	}

	@Override
	public UserBean validateUser(int userId) {
		return moodDao.validateUser(userId);
	}

}
