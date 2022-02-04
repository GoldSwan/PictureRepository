package com.swan.picturerepository.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.swan.picturerepository.dto.BulletinBoardDTO;
import com.swan.picturerepository.dto.UserFileInfoDTO;
import com.swan.picturerepository.service.BulletinboardService;
import com.swan.picturerepository.service.FileSearchService;
import com.swan.picturerepository.service.HashTagService;
import com.swan.picturerepository.service.PageNavicationService;

@Controller
@RequestMapping("/bulletinboards")
public class SearchController {
	
	@Autowired private BulletinboardService bulletinboardService;
	@Autowired private FileSearchService fileSearchService;
	@Autowired private PageNavicationService pageNavicationService;
	@Autowired private HashTagService hashTagService;
	
	List<BulletinBoardDTO> boardList = null;
	
	@RequestMapping(value = "", method = {RequestMethod.GET})
	public ModelAndView doSearchBulletinboards(@RequestParam("searchtype") String strSearchType, @RequestParam("search") String strSearch, @RequestParam("page") String strPage) throws JsonProcessingException {	
		ModelAndView mv = new ModelAndView();

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
			mv.setViewName("board/home");
			return mv;
		}
		maxImageCnt = pageNavicationService.getMAX_IMAGE_CNT();
		boardList = bulletinboardService.getSearchBoardList(strSearchType, strSearch, page, maxImageCnt);
		//searchCnt = fileList.size();	
		searchCnt = bulletinboardService.getSearchBulletinboardCnt(strSearchType, strSearch);
		maxPage = pageNavicationService.getMaxPage(searchCnt);
		startPage = pageNavicationService.getStartPage(page);
		endPage =pageNavicationService.getEndPage(page, maxPage);	
		isPreviousPage = pageNavicationService.getIsPreviousPage(startPage);		
		isNextPage = pageNavicationService.getIsNextPage(maxPage, endPage);
		
		List<Map<String, String>> list = new ArrayList<>();

		//2021-06-11 KSW : 페이징 처리된 데이터를 그대로 보여주는 것으로 수정
		for(int i = 0; i<boardList.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("bulletinId", boardList.get(i).getBulletinId().toString());//2021-09-11 KSW : 게시판 ID(bulletinId) 추가
			map.put("image", boardList.get(i).getRepresentativeFileId());
			map.put("like", boardList.get(i).getLikeFlag());
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
		mv.addObject("searchtype",strSearchType);
		
		mv.setViewName("board/home");
		 
		return mv;
	}
	
	@RequestMapping(value = "/{bulletinId}", method = {RequestMethod.GET})
	public ModelAndView doSearchBulletinboardById(@PathVariable("bulletinId") String strbulletinId) {	
		List<UserFileInfoDTO> fileList = null;
		List<String> tagList = null;
		String strTitle = "";
		String strIsrtDt = "";
		String strUsername = "";
		String strContent = "";
		String strLikeCnt = "";
		String strLikeFlag = "";
		
		String strSearchDataMap = "";
		
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mv.setViewName(".notdynamicjs/board/imageView");
		fileList = fileSearchService.getSearchFileListByFileId(strbulletinId);	
		List<Map<String, String>> listImages = new ArrayList<>();
		List<Map<String, String>> listTags = new ArrayList<>();
		
		if(fileList!=null && fileList.size()>0) {
			strTitle = fileList.get(0).getTitle();
			strIsrtDt = fileList.get(0).getIsrtDt();
			strUsername = fileList.get(0).getUsername();
			strContent = fileList.get(0).getContent();
			strLikeCnt = fileList.get(0).getLikeCnt();
			strLikeFlag = fileList.get(0).getLikeFlag();

			for(int i = 0; i<fileList.size();i++) {
				Map<String, String> map = new HashMap<>();				
				map.put("image", fileList.get(i).getFileId());
				listImages.add(map);
			}
		}
		
		tagList = hashTagService.getSearchTagList(strbulletinId);
		
		if(tagList!=null && tagList.size()>0) {
			for(String strTagName : tagList) {
				Map<String, String> map = new HashMap<>();
				map.put("tag", strTagName);
				listTags.add(map);
			}
		}
		
		Map<String, Object> searchDataMap = new HashMap<>();	
		searchDataMap.put("imageData", listImages);
		searchDataMap.put("tagData", listTags);
		strSearchDataMap = new Gson().toJson(searchDataMap);
		
		mv.addObject("searchDataMap",strSearchDataMap);
		mv.addObject("title",strTitle);
		mv.addObject("isrtDt",strIsrtDt);
		mv.addObject("username",strUsername);
		mv.addObject("content",strContent);
		mv.addObject("likeCnt",strLikeCnt);
		mv.addObject("likeFlag",strLikeFlag);
		mv.addObject("bulletinId", strbulletinId);
		
		return mv;
	}
}
