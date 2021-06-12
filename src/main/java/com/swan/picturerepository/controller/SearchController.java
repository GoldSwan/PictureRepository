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
		int maxImageCnt = 0;//한 페이지에 나올 수 있는 최대 이미지 갯수
		int startPage = 0;//요청 page 기준으로 하단 네이게이션의 처음 순번의 페이지
		int endPage = 0;//요청 page 기준으로 하단 네이게이션의  마지막 순번의 페이지
		int maxPage = 0;//조회한 데이터의 마지막 페이지
		int searchCnt = 0;//검색 데이터 갯수
		boolean isPreviousPage = false;
		boolean isNextPage = false;

		//page가 숫자가 아닌 요청이 들어왔을 때 exception 처리
		try {
			page = Integer.parseInt(strPage);
		}catch(NumberFormatException e){
			mv.setViewName("home");
			return mv;
		}
		maxImageCnt = pageNavicationService.getMAX_IMAGE_CNT();
		fileList = fileSearchService.getSearchFileList(strSearch, page, maxImageCnt);
		//searchCnt = fileList.size();	
		searchCnt = fileSearchService.getSearchFileCnt(strSearch);
		maxPage = pageNavicationService.getMaxPage(searchCnt);
		startPage = pageNavicationService.getStartPage(page);
		endPage =pageNavicationService.getEndPage(page, maxPage);	
		isPreviousPage = pageNavicationService.getIsPreviousPage(startPage);		
		isNextPage = pageNavicationService.getIsNextPage(maxPage, endPage);
		
		List<Map<String, String>> list = new ArrayList<>();

		//2021-06-11 KSW : 페이징 처리된 데이터를 그대로 보여주는 것으로 수정
		for(int i = 0; i<fileList.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("image", fileList.get(i).getFileId());
			map.put("like", fileList.get(i).getLikeFlag());
			list.add(map);
		}
		//페이지별 16개씩만 짤라서 전송
		//2021-06-11 KSW : 주석치리
		/*
		for(int i = dataIndex; i<dataMaxRange;i++) {
			Map<String, String> map = new HashMap<>();
			map.put("image", fileList.get(i).getFileId());
			map.put("like", fileList.get(i).getLikeFlag());
			list.add(map);
		}	
        */
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
