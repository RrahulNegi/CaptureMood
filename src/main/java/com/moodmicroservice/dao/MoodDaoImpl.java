package com.moodmicroservice.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.moodmicroservice.bean.EmployeeSubmissionBean;
import com.moodmicroservice.bean.MailBean;
import com.moodmicroservice.bean.MoodBean;
import com.moodmicroservice.bean.UserBean;

@Repository("moodDao")
public class MoodDaoImpl implements MoodDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MongoOperations mongoOperation;

	@Override
	public String addMood(MoodBean moodBean) throws Exception {
		logger.info("Calling capture mood in dao..");
		String result = "success";
		try {

			Query query = new Query();
			Date now = new Date();
			query.addCriteria(Criteria.where("projectName").is(moodBean.getProjectName()));

			Update update = new Update();
			update.set("happyState", moodBean.getHappyState());
			update.set("sadState", moodBean.getSadState());
			update.set("normalState", moodBean.getNormalState());

			update.set("date", now);
			logger.info("query :", query!=null?query.toString():"error");
			mongoOperation.upsert(query, update, MoodBean.class);
			// mongoOperation.save(moodBean);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			result = "error";
		}
		return result;
	}

	@Override
	public List<MoodBean> findMoodOnDate(Date date, String projectName, Date fromDate, Date toDate) {

		logger.info("Calling find mood in dao..");

		Query qry = new Query();
		List<MoodBean> list = null;

		try {

			qry.addCriteria(Criteria.where("projectName").is(projectName).and("date").lte(toDate).gte(fromDate));

			logger.info("query : "+qry!=null?qry.toString():"error");


			list = mongoOperation.find(qry, MoodBean.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}

		return list;
	}

	@Override
	public MoodBean getTodayMood(Date date, String projectName) {
		logger.info("Calling todays mood..");
		MoodBean mdb = new MoodBean();
		Query qry = new Query();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.DATE, 1);
		Date newDate = new Date();
		newDate = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// System.out.println(dateFormat.parse(dateFormat.format(newDate))+"::"+dateFormat.parse(dateFormat.format(date)));
			Date dDate = dateFormat.parse(dateFormat.format(date));
			Date nDate = dateFormat.parse(dateFormat.format(newDate));
			qry.addCriteria(Criteria.where("projectName").is(projectName).and("date").lte(nDate).gte(dDate));
			mdb = mongoOperation.findOne(qry, MoodBean.class);
			logger.info("query :"+ qry!=null?qry.toString():"error");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return mdb;
	}

	@Override
	public String addEmployeeSubmission(EmployeeSubmissionBean empBean) {
		logger.info("Calling employee submission..");
		String result = "success";
		try {
			mongoOperation.save(empBean);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			result = "error";
		}
		return result;
	}

	@Override
	public void updateUser(int userId) throws Exception {

		logger.info("Calling update user ..");

		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		Update update = new Update();
		update.set("moodFlag", 1);
		logger.info("query :"+ query!=null?query.toString():"error");
		mongoOperation.updateFirst(query, update, UserBean.class);

	}

	@Override
	public List<UserBean> getUsertoSendMail() {
		logger.info("Calling user info to send mail..");

		Query query = new Query();
		query.addCriteria(Criteria.where("moodFlag").is(0));
		logger.info("query :"+query!=null?query.toString():"error");
		List<UserBean> userBeanList = mongoOperation.find(query, UserBean.class);
		
		return userBeanList;
	}

	public MailBean getMailData(int mailId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("mailId").is(mailId));
		MailBean mailData = mongoOperation.findOne(query, MailBean.class);
		logger.info("query :"+ query!=null?query.toString():"error");

		return mailData;

	}

	@Override
	public String resetUserTablemoodFlag() {

		logger.info("Calling reset user mood..");

		String result = "success";
		try {
			Query query = new Query();
			// query.addCriteria(Criteria.where("userId").is(userId));
			Update update = new Update();
			update.set("moodFlag", 0);
			mongoOperation.updateMulti(query, update, UserBean.class);
			logger.info("query :"+ query!=null?query.toString():"error");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			result = "failure";
		}
		return result;
	}

	@Override
	public int getUserMoodFlag(int userId) {
		// TODO Auto-generated method stub

		logger.info("Calling get user mood..");

		Query query = new Query();
		int moodFlag = 0;
		query.addCriteria(Criteria.where("userId").is(userId));
		UserBean ub = mongoOperation.findOne(query, UserBean.class);
		logger.info("query :"+ query!=null?query.toString():"error");

		moodFlag = ub.getMoodFlag();
		return moodFlag;
	}

	@Override
	public UserBean validateUser(int userId) {
		// TODO Auto-generated method stub
		logger.info("Calling validate user..");

		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		UserBean ub = mongoOperation.findOne(query, UserBean.class);
		logger.info("query :"+ query!=null?query.toString():"error");

		return ub;
	}

}
