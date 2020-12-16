package com.swan.picturerepository.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swan.picturerepository.model.User;
import com.swan.picturerepository.service.UserJoinService;

@Controller
public class JoinController {
	
	@Autowired
	private UserJoinService userJoinService;
	
	@RequestMapping(value = "/join.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String doJoin(HttpServletRequest req, Model model,@Valid User user, BindingResult result) {
		
		String password = user.getPassword();
		String confirmPassword = req.getParameter("confirmPassword");
		System.out.println(user.getUsername());
		if(result.hasErrors()) {			
			if(!userJoinService.reConfirmPassword(password, confirmPassword))
				model.addAttribute("errorMsg", "비밀번호가 일치하지 않습니다.");			
			
			model.addAttribute("confirmPassword",confirmPassword);		
			
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
	
	@RequestMapping(value = "/async-email-valid.do", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, List<Map<String, String>>> asyncEmailValidDo(HttpServletRequest req, Model model,@Valid User user, BindingResult result) {
		Map<String, List<Map<String, String>>> results = new HashMap<>();
		List<Map<String, String>> errorList = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		errorList.add(map);		
		
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			
			for(ObjectError error:errors) {
				System.out.println(error.getDefaultMessage());
				if(error.getDefaultMessage().equals("이메일을 입력해주세요.") || error.getDefaultMessage().equals("이메일 주소가 유효하지 않습니다.")) {
					map.put("email_error", error.getDefaultMessage());
				}
			}
		}
		
		results.put("errorlist", errorList);
		
		return results;
	}
}
