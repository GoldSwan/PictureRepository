package com.swan.picturerepository.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.swan.picturerepository.dto.UserFileInfoDTO;
import com.swan.picturerepository.service.BulletinboardService;

@Controller
public class ImageViewController {
	@Autowired private BulletinboardService bulletinboardService;
	List<UserFileInfoDTO> fileList = null;
	String strbulletinId = "";//2021-09-11 KSW : 게시판 ID(bulletinId) 추가
	String strUsername = "";
	String strTitle = "";
	String strContent = "";
	String strTag = "";
	String strIsrtDt = "";
	String strSearchDataMap = "";
	@RequestMapping(value = "/move/imageView", method = {RequestMethod.GET})
	public ModelAndView showImageView(HttpServletRequest req, Model model) {	
		
		ModelAndView mv = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mv.setViewName("board/imageView");
		strbulletinId = req.getParameter("bulletinId");
		strUsername = req.getParameter("username");
		fileList = bulletinboardService.getSearchFileListBybulletinId(strbulletinId);
		List<Map<String, String>> list = new ArrayList<>();
		if(fileList!=null && fileList.size()>0) {
			//strTitle = fileList.get(0).getTitle();
			//strContent = fileList.get(0).getContent();
			//strTag = fileList.get(0).getTag();
			strTitle = "";
			strContent = "";
			strTag = "";
			strIsrtDt = sdf.format(fileList.get(0).getIsrtDt());			

			for(int i = 0; i<fileList.size();i++) {
				Map<String, String> map = new HashMap<>();				
				map.put("image", fileList.get(i).getFileId());
				list.add(map);
			}
		}
		
		Map<String, Object> searchDataMap = new HashMap<>();	
		searchDataMap.put("imageData", list);
		strSearchDataMap = new Gson().toJson(searchDataMap);
		
		mv.addObject("searchDataMap",strSearchDataMap);
		mv.addObject("username",strUsername);
		mv.addObject("title",strTitle);
		mv.addObject("content",strContent);
		mv.addObject("tag",strTag);
		mv.addObject("isrtDt",strIsrtDt);
		
		return mv;
	}
	@RequestMapping(value = "/move/imageViewResult", method = {RequestMethod.GET})
	public ModelAndView showImageViewResult(HttpServletRequest req, Model model) {	
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/imageView");
		
		strUsername = req.getParameter("username");
		strSearchDataMap = req.getParameter("searchDataMap");
		strTitle = req.getParameter("title");
		strContent =  req.getParameter("content");
		strTag =  req.getParameter("tag");
		strIsrtDt = req.getParameter("isrtDt");
		
		mv.addObject("searchDataMap",strSearchDataMap);
		mv.addObject("username",strUsername);
		mv.addObject("title",strTitle);
		mv.addObject("content",strContent);
		mv.addObject("tag",strTag);
		mv.addObject("isrtDt",strIsrtDt);
		return mv;
	}	
}
