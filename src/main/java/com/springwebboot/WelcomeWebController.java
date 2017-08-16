package com.springwebboot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeWebController {

	// inject via application.properties
		@Value("${welcome.message:test}")
		private String message = "Hello World";

		@RequestMapping("/")
		public String welcome(Map<String, Object> model) {
			model.put("message", this.message);
			return "welcome";
		}
		
		@RequestMapping("/show")
		public String welcomeShow(Map<String, Object> model) {
			model.put("message", "Hello Rahul Singh Negi");
			return "welcome2";
		}

}
