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
	final int MAX_IMAGE_CNT = 16;//페이지 당 있을 수 있는 MAX 이미지 수
	
	@RequestMapping(value = "/search", method = {RequestMethod.GET})
	public ModelAndView doSearch(HttpServletRequest req) throws JsonProcessingException {	
		ModelAndView mv = new ModelAndView();
		String strSearch = req.getParameter("search");
		String strPage = req.getParameter("page");
		int page = 1;
		
		try {
			page = Integer.parseInt(strPage);
		}catch(NumberFormatException e){
			mv.setViewName("home");
			return mv;
		}
		
		fileList = fileSearchService.getSearchFileList(strSearch);
		
		if(fileList.size()==0) {
			mv.addObject("noData","검색된 데이터가 없습니다.");
		}
		
		List<Map<String, String>> list = new ArrayList<>();
		int pageIndex = MAX_IMAGE_CNT * (page-1);
		int maxRange = MAX_IMAGE_CNT * page <= fileList.size() ? MAX_IMAGE_CNT * page : fileList.size();
		//페이지별 16개씩만 짤라서 전송
		for(int i = pageIndex; i<maxRange;i++) {
			Map<String, String> map = new HashMap<>();
			map.put("image", fileList.get(i).getFileId());
			list.add(map);
		}		

		String searchData = new Gson().toJson(list);
		System.out.println(searchData);
		
		mv.addObject("searchData",searchData);
		mv.setViewName("home");
		 
		return mv;
	}

}
