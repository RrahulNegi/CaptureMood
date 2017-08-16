package com.moodmicroservice.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.moodmicroservice.bean.MailBean;
import com.moodmicroservice.bean.MoodBean;
import com.moodmicroservice.bean.MoodReportBean;
import com.moodmicroservice.bean.ResponseBean;
import com.moodmicroservice.bean.UserBean;
import com.moodmicroservice.formbean.MoodFormBean;
import com.moodmicroservice.service.MoodService;
import com.moodmicroservice.util.EncryptionDecryption;

@RestController
public class MoodController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MoodService moodService;
	@Value("${mood.addmood.url}")
	String addMoodUrl;
	@Value("${mood.mail.url}")
	String mailUrl;

	@RequestMapping(value = "/addMood", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBean submitMood(@RequestBody MoodFormBean moodFrmBean) {
		logger.info("Capture Mood");
		ResponseBean resBean = moodService.addMood(moodFrmBean);

		return resBean;
	}

	@RequestMapping(value = "/addMoodByMail/{tokenId}/{projectId}/{moodStatus}/{userId}")

	public String submitMoodByMail(@PathVariable String tokenId, @PathVariable String projectId,
			@PathVariable String moodStatus, @PathVariable String userId) {

		logger.info("Capture Mood by mail for project " + projectId);

		EncryptionDecryption ed = new EncryptionDecryption();

		System.out.println("In Capture Mood by mail");
		String mailId = ed.decrypt(tokenId);
		System.out.println("Decrypt mail Id " + mailId);

		String projId = projectId;
		String mood = moodStatus;
		String usrId = userId;
		MoodFormBean mfb = new MoodFormBean();
		mfb.setUserId(Integer.parseInt(usrId));
		mfb.setProjectName(projectId);
		mfb.setMoodState(mood);
		ResponseBean response = submitMood(mfb);

		if (null != response.getSuccessCode())
			return "Mood Submited Successfully";
		else
			return "Error";

	}

	@RequestMapping(value = "/getMood/{projectName}/{fromDate}/{toDate}/{randomTime}")
	public MoodReportBean getMood(@PathVariable String projectName, @PathVariable String fromDate,
			@PathVariable String toDate) {

		logger.info("Get Mood Detail for project " + projectName + " from: " + fromDate + " to: " + toDate);

		Date now = new Date();
		System.out.println("IN GET MOOOD " + projectName);

		MoodReportBean list = moodService.getMoodOnDate(now, projectName, fromDate, toDate);
		MoodBean mb=new MoodBean();
		/*for(MoodBean m:list){
			mb.setHappyState(m.getHappyState());
			mb.setNormalState(m.getNormalState());
			mb.setSadState(m.getSadState());
		}*/
		//sendMail();
		return list;

	}
	
	/*@RequestMapping(value = "/getMood1/{projectName}/{fromDate}/{toDate}")

	public MoodReportBean getMood1(@PathVariable String projectName, @PathVariable String fromDate,
			@PathVariable String toDate) {

		logger.info("Get Mood Detail for project " + projectName + " from: " + fromDate + " to: " + toDate);

		Date now = new Date();
		System.out.println("IN GET MOOOD " + projectName);
		MoodBean md=new MoodBean();
		List<MoodBean> list = moodService.getMoodOnDate(now, projectName, fromDate, toDate);
		for(MoodBean b:list){
			md.setHappyState(b.getHappyState());
		}
		MoodReportBean mrb=new MoodReportBean();
		mrb.setChartData("TESTINGrrrrrrrrrrrrrrrr");

		// cronJob();
		return mrb;

	}*/

	@RequestMapping(value = "/validateUser/{userId}")
	@ResponseBody
	public UserBean validateUser(@PathVariable String userId) {

		logger.info("Validate User " + userId);

		UserBean userBean = moodService.validateUser(Integer.parseInt(userId));

		return userBean;

	}

	private AtomicInteger counter = new AtomicInteger(0);

	// @Scheduled(cron = "0 0/5 * * * *")
	public void sendMail() {

		logger.info("Sending mail scheduler running....");

		EncryptionDecryption ed = new EncryptionDecryption();
		RestTemplate restTemplate = new RestTemplate();
		String to;
		String userName = null;
		String projectName = null;
		// String addMoodUrl =
		// "http://localhost:8080/captureMood/addMoodByMail/";
		// String urlString = "http://localhost:8888/SpringBootDemo/sendMail";
		MailBean mail = moodService.getMailDetail(1);
		String mailSubject = mail.getMailSubject();
		String mailBody = mail.getMailBody();
		String[] imagePath = mail.getImagePath();
		List<UserBean> listUserSendMail = moodService.sendMailtoUser();
		String newAddMoodUrl = "";
		String greatMoodUrl = null;
		String goodMoodUrl = null;
		String badMoodUrl = null;
		List<String> userList = new LinkedList<String>();
		for (UserBean ub : listUserSendMail) {

			to = ub.getEmailId();
			userName = ub.getUserName();
			userList.add(to);
			String tokenId = ed.encrypt(to);
			int user = ub.getUserId();
			projectName = ub.getProjectName();
			newAddMoodUrl = addMoodUrl + tokenId;
			greatMoodUrl = newAddMoodUrl + "/" + projectName + "/happy/" + user;
			goodMoodUrl = newAddMoodUrl + "/" + projectName + "/normal/" + user;
			badMoodUrl = newAddMoodUrl + "/" + projectName + "/sad/" + user;
			String newMailBody = mailBody.replaceAll("NAME", userName);
			Map<String, String> input = new HashMap<String, String>();
			input.put("NAME", userName);
			input.put("GREAT_URL", greatMoodUrl);
			input.put("GOOD_URL", goodMoodUrl);
			input.put("BAD_URL", badMoodUrl);

			newMailBody = newMailBody.replace("URL", newAddMoodUrl);

			String htmlText = readEmailFromHtml("mailBodyTemplate/moodMailBody.html", input);
			JSONObject request = new JSONObject();
			request.put("to", to);
			request.put("from", "teammood.in@soprasteria.com");
			request.put("subject", mailSubject);
			request.put("mailBody", htmlText);
			request.put("imagePath", "img/great.png,img/good.png,img/sad.png");

			// set headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
			ResponseEntity<String> loginResponse = restTemplate.exchange(mailUrl, HttpMethod.POST, entity,
					String.class);

		}

	}

	// @Scheduled(cron = "0 0/5 * * * *")
	public void resetTableSchedular() {
		logger.info("Reset user moodflag scheduler running....");

		String result = moodService.resetUserTablemoodFlag();
		if ("success".equals(result)) {
			logger.info("Reset Successfulllly");
		}
	}

	protected String readEmailFromHtml(String filePath, Map<String, String> input) {
		String msg = readContentFromFile(filePath);
		try {
			Set<Entry<String, String>> entries = input.entrySet();
			for (Map.Entry<String, String> entry : entries) {
				msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return msg;
	}

	// Method to read HTML file as a String
	private String readContentFromFile(String fileName) {
		StringBuffer contents = new StringBuffer();
		ClassLoader classLoader = getClass().getClassLoader();

		File file = new File(classLoader.getResource(fileName).getFile());

		try {
			// use buffering, reading one line at a time
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line = null;
			while ((line = reader.readLine()) != null) {
				contents.append(line);
				contents.append(System.getProperty("line.separator"));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return contents.toString();

	}
}
