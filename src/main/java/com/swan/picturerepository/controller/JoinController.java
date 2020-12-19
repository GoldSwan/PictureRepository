package com.swan.picturerepository.controller;

import java.util.HashMap;
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
	public @ResponseBody Map<String, String> asyncEmailValidDo(HttpServletRequest req, Model model,@Valid User user, BindingResult result) {
		Map<String, String> map = new HashMap<>();
		
		map.put("emailError", "");
		
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			
			for(ObjectError error:errors) {
				String[] errorContentArray =  error.getArguments()[0].toString().split(";");
				if(errorContentArray.length<3)
					continue;
				String[] errorContentDetailArray = errorContentArray[2].split(" ");
				if(errorContentDetailArray.length< 3)
					continue;
				String errorId = errorContentDetailArray[3].replace("[", "").replace("]","");
				if(errorId.equals("email"))
					map.put("emailError", error.getDefaultMessage());
			}
		}
		
		return map;
	}
}
