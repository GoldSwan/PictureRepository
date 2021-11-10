package com.swan.picturerepository.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.swan.picturerepository.service.FileUploadService;

@Controller
public class UploadController {
	
	@Resource(name = "imageUploadPath")
	private String imageUploadPath;
	@Resource(name = "thumbnailUploadPath")
	private String thumbnailUploadPath;
	@Resource(name = "fullUploadPath")
	private String fullUploadPath;
	@Autowired FileUploadService fileUploadService;
	
	@RequestMapping(value = "/uploadForm/multi", method = RequestMethod.POST)
	public ModelAndView uploadFormMulti(Model model, HttpServletRequest req, @RequestParam("file") List<MultipartFile> fileList) throws Exception {
		
		String strBulletinId = "";
		String strUsername = "";
		String strTitle = "";
		String strContent = "";
		String strTag = "";
		String strPublicRange = "";
		
		strUsername = req.getParameter("username");
		strTitle = req.getParameter("title");
		strContent = req.getParameter("content");
		strTag = req.getParameter("tag");
		strPublicRange = req.getParameter("publicRange");
		
		StringBuffer sb = new StringBuffer();
		ModelAndView mv = new ModelAndView();
		ArrayList<String> bulletinBoardInfoList = new ArrayList<>();	
		ArrayList<String> userFileInfoList = new ArrayList<>();
		
		bulletinBoardInfoList.add(0, strContent);
		bulletinBoardInfoList.add(1, "N");
		bulletinBoardInfoList.add(2, strPublicRange);
		bulletinBoardInfoList.add(3, strTitle);
		bulletinBoardInfoList.add(4, strUsername);
		
		for (MultipartFile file : fileList) {
			if (file.isEmpty()) {
				model.addAttribute("uploadMultiErrorMsg", "선택한 파일이 없습니다.");
				mv.setViewName("imageFileUpload");				
				return mv;
			}
		}

		for (int i = 0 ; i < fileList.size() ; i++) {
			UUID uid = UUID.randomUUID();
			String strFileName = fileList.get(i).getOriginalFilename();
			String strFileExtension = FilenameUtils.getExtension(strFileName);
			String strFileId = String.format("%s.%s",uid.toString(),strFileExtension);
			File imageFile = fileUploadService.uploadImageFile(imageUploadPath, strFileId, fileList.get(i).getBytes(), strFileName);
			fileUploadService.uploadThumbnailFile(thumbnailUploadPath, strFileId, imageFile, strFileName);
			fileUploadService.uploadFullThumbnailFile(fullUploadPath, strFileId, imageFile, strFileName);
			userFileInfoList.add(i,strFileId);		
			
			sb.append(strFileName).append(",");
		}
		
		strBulletinId = fileUploadService.createFileData(bulletinBoardInfoList, userFileInfoList);

		if(strBulletinId == "") {
			model.addAttribute("uploadMultiErrorMsg", "업로드에서 에러가 발생했습니다.");
			mv.setViewName("imageFileUpload");				
			return mv;
		}
		//redirect : 사진 업로드가 완료되면  imageFileUploadResult 화면으로 이동
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(req.getContextPath()+"/bulletinboards/" + strBulletinId);
		mv.setView(redirectView);
		/*
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, Object> searchDataMap = new HashMap<>();	
		
		
		for(int i = 0; i<userFileInfoList.size();i++) {
			Map<String, String> map = new HashMap<>();
			map.put("image", userFileInfoList.get(i));
			list.add(map);
		}
		
		searchDataMap.put("imageData", list);
		String strSearchDataMap = new Gson().toJson(searchDataMap);
		
		mv.addObject("searchDataMap",strSearchDataMap);
		mv.addObject("username",strUsername);
		mv.addObject("title",strTitle);
		mv.addObject("content",strContent);
		mv.addObject("tag",strTag);
		mv.addObject("publicRange",strPublicRange);
		*/
		return mv;
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
