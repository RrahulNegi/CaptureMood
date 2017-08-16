package com.moodmicroservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavigateController {

	@RequestMapping(value="/mood")
	public String navigateMoodJsp(){
		System.out.println("in navigate");
		return "mood";
	}
}
