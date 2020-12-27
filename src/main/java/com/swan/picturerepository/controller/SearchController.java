package com.swan.picturerepository.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swan.picturerepository.model.UserFileInfo;
import com.swan.picturerepository.service.FileSearchService;

@Controller
public class SearchController {
	
	@Autowired private FileSearchService fileSearchService;
	List<UserFileInfo> fileList = null;
	
	@RequestMapping(value = "/search", method = {RequestMethod.GET})
	public String doJoin(HttpServletRequest req, Model model) {
		
		String strSearch = req.getParameter("search");	
		fileList = fileSearchService.getSearchFileList(strSearch);
		
		for(UserFileInfo userFileInfo : fileList) {
			System.out.println(userFileInfo.getFileId());
		}
		//model.addAttribute("confirmPassword",confirmPassword);
		 
		return "home";
	}

}
