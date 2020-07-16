package com.swan.picturerepository.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swan.picturerepository.model.User;
import com.swan.picturerepository.service.UserJoinService;

@Controller
public class JoinController {
	
	@Autowired
	private UserJoinService userJoinService;
	
	@RequestMapping(value = "/join.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String doJoin(Model model,@Valid User user, BindingResult result) {
		
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			
			for(ObjectError error:errors) {
				System.out.println(error.getDefaultMessage());
			}
			
			return "join";
		}
		
		userJoinService.createUser(user);
		return "joinSuccess";
	}
	
	@RequestMapping(value = "/join", method = {RequestMethod.GET})
	public String showJoin(Model model) {
			
		model.addAttribute("user",new User());
		
		return "join";
	}
}
