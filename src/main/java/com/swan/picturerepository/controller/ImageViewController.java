package com.swan.picturerepository.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.swan.picturerepository.model.UserFileInfo;
import com.swan.picturerepository.service.FileSearchService;

@Controller
public class ImageViewController {
	@Autowired private FileSearchService fileSearchService;
	List<UserFileInfo> fileList = null;
	String strFileId = "";
	String strUsername = "";
	String strTitle = "";
	String strContent = "";
	String strTag = "";
	@RequestMapping(value = "/move/imageView", method = {RequestMethod.GET})
	public ModelAndView showImageView(HttpServletRequest req, Model model) {	
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("imageView");
		strFileId = req.getParameter("fileId");	
		strUsername = req.getParameter("username");
		fileList = fileSearchService.getSearchFileListByFileId(strFileId);	
		if(fileList!=null && fileList.size()>0) {
			strTitle = fileList.get(0).getTitle();
			strContent = fileList.get(0).getContent();
			strTag = fileList.get(0).getTag();
		}
		
		mv.addObject("fileId",strFileId);
		mv.addObject("username",strUsername);
		mv.addObject("title",strTitle);
		mv.addObject("content",strContent);
		mv.addObject("tag",strTag);
		
		return mv;
	}
}
