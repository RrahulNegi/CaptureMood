package com.moodmicroservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moodmicroservice.repository.UserRepository;
import com.springwebboot.bean.UserBean;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MongoOperations mongoOperations;

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public String addUser(@ModelAttribute UserBean userBean) {
		System.out.println(userBean.getProjectName());
		userRepository.save(userBean);
		return "User save successfully";

	}
	
	@RequestMapping(value = "/findUser/{username}", method = RequestMethod.GET)
	@ResponseBody
	public List<UserBean>findUser(@PathVariable String username){
		System.out.println("UserName "+username);
		userRepository.findOne(username);
		Query qry=new Query();
		qry.addCriteria(Criteria.where("username").is(username));
		List<UserBean> ubList=new ArrayList<UserBean>();
		ubList=mongoOperations.find(qry, UserBean.class);
		return ubList;
		
	}

	
	
}
