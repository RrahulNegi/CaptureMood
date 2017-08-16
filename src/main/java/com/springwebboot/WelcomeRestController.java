package com.springwebboot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springwebboot.bean.EmployeeBean;

@RestController
public class WelcomeRestController {
	@RequestMapping(value="/getEmp")
	@ResponseBody
	public List<EmployeeBean> showEmployee(){
		EmployeeBean e1=new EmployeeBean();
		e1.setUserId(1);
		e1.setUserName("Rahul");
		e1.setProjectName("Java");
		EmployeeBean e2=new EmployeeBean();
		e2.setUserId(2);
		e2.setUserName("Rahul Singh Negi");
		e2.setProjectName("Spring Boot");
		List<EmployeeBean> list=new ArrayList<EmployeeBean>();
		list.add(e1);
		list.add(e2);
		return list;
	}

}
