package com.swan.picturerepository.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.swan.picturerepository.model.UserFileInfo;
import com.swan.picturerepository.service.FileSearchService;

@Controller
public class SearchController {
	
	@Autowired private FileSearchService fileSearchService;
	List<UserFileInfo> fileList = null;
	
	@RequestMapping(value = "/search", method = {RequestMethod.GET})
	public ModelAndView doSearch(HttpServletRequest req) throws JsonProcessingException {	
		ModelAndView mv = new ModelAndView();
		String strSearch = req.getParameter("search");	
		fileList = fileSearchService.getSearchFileList(strSearch);
		List<Map<String, String>> list = new ArrayList<>();
		
		for(UserFileInfo userFileInfo : fileList) {
			Map<String, String> map = new HashMap<>();
			map.put("image", userFileInfo.getFileId());
			list.add(map);
		}
		String searchData = new Gson().toJson(list);
		System.out.println(searchData);
		
		mv.addObject("searchData",searchData);
		mv.setViewName("home");
		 
		return mv;
	}

}
