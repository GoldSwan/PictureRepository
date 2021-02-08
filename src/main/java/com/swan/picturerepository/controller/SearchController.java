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
import com.swan.picturerepository.service.PageNavicationService;

@Controller
public class SearchController {
	
	@Autowired private FileSearchService fileSearchService;
	@Autowired private PageNavicationService pageNavicationService;
	List<UserFileInfo> fileList = null;
	
	@RequestMapping(value = "/search", method = {RequestMethod.GET})
	public ModelAndView doSearch(HttpServletRequest req) throws JsonProcessingException {	
		ModelAndView mv = new ModelAndView();
		String strSearch = req.getParameter("search");
		String strPage = req.getParameter("page");
		int page = 0;
		int startPage = 0;//요청 page 기준으로 하단 네이게이션의 처음 순번의 페이지
		int endPage = 0;//요청 page 기준으로 하단 네이게이션의  마지막 순번의 페이지
		int maxPage = 0;//조회한 데이터의 마지막 페이지
		int searchCnt = 0;//검색 데이터 갯수
		int dataIndex = 0;
		int dataMaxRange = 0;
		boolean isPreviousPage = false;
		boolean isNextPage = false;

		//page가 숫자가 아닌 요청이 들어왔을 때 exception 처리
		try {
			page = Integer.parseInt(strPage);
		}catch(NumberFormatException e){
			mv.setViewName("home");
			return mv;
		}
		
		fileList = fileSearchService.getSearchFileList(strSearch);
		searchCnt = fileList.size();
		maxPage = pageNavicationService.getMaxPage(searchCnt);
		startPage = pageNavicationService.getStartPage(page);
		endPage =pageNavicationService.getEndPage(page, maxPage);	
		isPreviousPage = pageNavicationService.getIsPreviousPage(startPage);		
		isNextPage = pageNavicationService.getIsNextPage(maxPage, endPage);
		dataIndex = pageNavicationService.getDataIndex(page);
		dataMaxRange = pageNavicationService.getDataMaxRange(page, searchCnt);
		
		List<Map<String, String>> list = new ArrayList<>();

		//페이지별 16개씩만 짤라서 전송
		for(int i = dataIndex; i<dataMaxRange;i++) {
			Map<String, String> map = new HashMap<>();
			map.put("image", fileList.get(i).getFileId());
			map.put("like", fileList.get(i).getLikeFlag());
			list.add(map);
		}	

		if(searchCnt==0) {
			mv.addObject("noData","검색된 데이터가 없습니다.");
		}

		Map<String, Object> searchDataMap = new HashMap<>();
		
		searchDataMap.put("imageData", list);
		searchDataMap.put("startPage",startPage);
		searchDataMap.put("endPage",endPage);
		searchDataMap.put("isPreviousPage",isPreviousPage);
		searchDataMap.put("isNextPage",isNextPage);		
		
		String strSearchDataMap = new Gson().toJson(searchDataMap);
		mv.addObject("searchDataMap",strSearchDataMap);
		mv.addObject("search",strSearch);
		
		mv.setViewName("home");
		 
		return mv;
	}

}
