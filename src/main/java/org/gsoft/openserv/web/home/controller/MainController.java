package org.gsoft.openserv.web.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@RequestMapping(value="home/home.do")
	public ModelAndView loanMainPage(ModelAndView model){
		return new ModelAndView("home/index");
	}
	
	@RequestMapping(value="testmask")
	public ModelAndView loadTest(ModelAndView model){
		return new ModelAndView("testmask");
	}
}
