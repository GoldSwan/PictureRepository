package com.swan.picturerepository.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.swan.picturerepository.service.FileUploadService;

@Controller
public class UploadController {
	
	@Resource(name = "imageUploadPath")
	private String imageUploadPath;
	@Resource(name = "thumbnailUploadPath")
	private String thumbnailUploadPath;
	@Autowired FileUploadService fileUploadService;
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
	public String uploadForm(Model model, HttpServletRequest req, @RequestParam("file") MultipartFile file) throws Exception {
		 
		String strUsername = req.getParameter("username");
		String strTitle = req.getParameter("title");
		String strContent = req.getParameter("content");
		String strTag = req.getParameter("tag");
		String strPublicRange = req.getParameter("publicRange");
		
		model.addAttribute("username", strUsername);
		
		if(file.isEmpty()) {
			model.addAttribute("uploadErrorMsg", "선택한 파일이 없습니다.");
			return "imageFileUpload";
		}
		
		UUID uid = UUID.randomUUID();
		String strFileName = file.getOriginalFilename();
		String strFileId = uid.toString() + strFileName;
		
		ArrayList<String> userInfoList = new ArrayList<>();
		
		userInfoList.add(strUsername);
		userInfoList.add(strFileId);
		userInfoList.add(strFileName);
		userInfoList.add(strTitle);
		userInfoList.add(strContent);
		userInfoList.add(strTag);
		userInfoList.add(strPublicRange);
		
		File imageFile = fileUploadService.uploadImageFile(imageUploadPath, strFileId, file.getBytes(), strFileName);
		fileUploadService.uploadThumbnailFile(thumbnailUploadPath, strFileId, imageFile, strFileName);
		fileUploadService.createFileData(userInfoList);
		model.addAttribute("savedImageName", strFileName);

		return "imageFileUploadResult";
	}

	@RequestMapping(value = "/uploadForm/multi", method = RequestMethod.POST)
	public ModelAndView uploadFormMulti(Model model, HttpServletRequest req, @RequestParam("file") List<MultipartFile> fileList) throws Exception {
		
		String strUsername = req.getParameter("username");
		String strTitle = req.getParameter("title");
		String strContent = req.getParameter("content");
		String strTag = req.getParameter("tag");
		String strPublicRange = req.getParameter("publicRange");
		String strUploadFileNames = "";
		StringBuffer sb = new StringBuffer();
		model.addAttribute("username", strUsername);
		model.addAttribute("title", strTitle);
		model.addAttribute("content", strContent);
		model.addAttribute("tag", strTag);
		ModelAndView mav = new ModelAndView();
		for (MultipartFile file : fileList) {
			if (file.isEmpty()) {
				model.addAttribute("uploadMultiErrorMsg", "선택한 파일이 없습니다.");
				mav.setViewName("imageFileUpload");				
				return mav;
			}
		}
		
		for (MultipartFile file : fileList) {
			UUID uid = UUID.randomUUID();
			String strFileName = file.getOriginalFilename();
			String strFileId = uid.toString() + strFileName;
			File imageFile = fileUploadService.uploadImageFile(imageUploadPath, strFileId, file.getBytes(), strFileName);
			fileUploadService.uploadThumbnailFile(thumbnailUploadPath, strFileId, imageFile, strFileName);
			
			ArrayList<String> userInfoList = new ArrayList<>();
			
			userInfoList.add(strUsername);
			userInfoList.add(strFileId);
			userInfoList.add(strFileName);
			userInfoList.add(strTitle);
			userInfoList.add(strContent);
			userInfoList.add(strTag);
			userInfoList.add(strPublicRange);
			sb.append(strFileName).append(",");
			fileUploadService.createFileData(userInfoList);
		}
		//redirect	
		strUploadFileNames = sb.toString();
		if(strUploadFileNames.length() > 0 ) {
			strUploadFileNames = strUploadFileNames.substring(0,strUploadFileNames.length()-1);
		}
		
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(req.getContextPath()+"/move/imageFileUploadResult");
		mav.setView(redirectView);
		mav.addObject("username", strUsername);
		mav.addObject("uploadFileNames", strUploadFileNames);
		return mav;
	}
	
	@RequestMapping(value = "/move/imageFileUpload")
	public String moveUpload(Model model, @RequestParam("username") String username) {
		model.addAttribute("username", username);
		return "imageFileUpload";
	}
	
	@RequestMapping(value = "/move/imageFileUploadResult")
	public String moveUploadResult(Model model, @RequestParam("uploadFileNames") String uploadFileNames, @RequestParam("username") String username) {
		model.addAttribute("username", username);
		model.addAttribute("uploadFileNames", uploadFileNames);
		return "imageFileUploadResult";
	}
}
