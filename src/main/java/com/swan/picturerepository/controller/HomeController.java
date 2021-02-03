package com.swan.picturerepository.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "/home";
	}
	@RequestMapping(value = "/loginUserinfoPopup", method = RequestMethod.GET)
	public ModelAndView openLoginUserinfoPopup() {
		ModelAndView mv = new ModelAndView();
		 mv.setViewName("loginUserinfoPopup");
		return mv;
	}
}
