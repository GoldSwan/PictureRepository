package com.swan.picturerepository.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ImageViewController {
	@RequestMapping(value = "/move/imageView", method = {RequestMethod.GET})
	public ModelAndView showImageView(HttpServletRequest req, Model model) {	
		ModelAndView mv = new ModelAndView();
		mv.setViewName("imageView");
		String strFileId = req.getParameter("fileId");
		String strUsername = req.getParameter("username");
		
		mv.addObject("fileId",strFileId);
		mv.addObject("username",strUsername);
		
		return mv;
	}
}
