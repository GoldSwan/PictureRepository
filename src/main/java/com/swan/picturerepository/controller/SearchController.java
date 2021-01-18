package com.swan.picturerepository.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	@Value("${constant.max_image_cnt}") private int MAX_IMAGE_CNT;//페이지 당 있을 수 있는 MAX 이미지 수
	@Value("${constant.max_nav_link_cnt}") private int MAX_NAV_LINK_CNT;//하단 네비게이션 링크의  MAX 수
	
	@RequestMapping(value = "/search", method = {RequestMethod.GET})
	public ModelAndView doSearch(HttpServletRequest req) throws JsonProcessingException {	
		ModelAndView mv = new ModelAndView();
		String strSearch = req.getParameter("search");
		String strPage = req.getParameter("page");
		int page = 1;
		int startPage = 1;
		int endPage = 1;
		int maxPage = 1;
		boolean isPreviousPage = false;
		boolean isNextPage = false;

		try {
			page = Integer.parseInt(strPage);
		}catch(NumberFormatException e){
			mv.setViewName("home");
			return mv;
		}
		startPage = page;
		endPage = page;
		
		//요청온 page가 어느범위에 속해있는지 구한다. startPage ~ endPage
		while (startPage%MAX_NAV_LINK_CNT != 1) {
			startPage -= 1;
		}
		while (endPage%MAX_NAV_LINK_CNT != 0) {
			endPage += 1;
		}

		fileList = fileSearchService.getSearchFileList(strSearch);
		maxPage = (int) Math.ceil((double) fileList.size() / MAX_IMAGE_CNT);
		endPage = endPage > maxPage ? maxPage : endPage;//마지막 페이지 endPage = (검색 이미지수 / 페이지 당 있을 수 있는 MAX 이미지 수)를 올림처리 
		isPreviousPage = startPage==1 ? false : true;
		isNextPage = maxPage==endPage ? false : true;
		
		//System.out.println("startPage:"+startPage);
		//System.out.println("endPage:"+endPage);
		
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
			map.put("like", fileList.get(i).getLikeFlag());
			list.add(map);
		}		

		String searchData = new Gson().toJson(list);
		//System.out.println(searchData);
		
		mv.addObject("searchData",searchData);
		mv.addObject("startPage",startPage);
		mv.addObject("endPage",endPage);
		mv.addObject("search",strSearch);
		mv.addObject("isPreviousPage",isPreviousPage);
		mv.addObject("isNextPage",isNextPage);
		mv.setViewName("home");
		 
		return mv;
	}

}
