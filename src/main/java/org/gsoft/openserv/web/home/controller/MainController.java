package org.gsoft.openserv.web.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping(value="home/home.do")
	public String loadMainPage(){
		return "home/index";
	}

	@RequestMapping(value="test")
	public String loadTestPage(){
		return "test";
	}
}
