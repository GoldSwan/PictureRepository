package com.swan.picturerepository.controller;

import java.io.File;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.swan.picturerepository.service.FileUploadService;

@Controller
public class UploadController {
	
	@Resource(name = "imageUploadPath")
	private String imageUploadPath;
	@Resource(name = "thumbnailUploadPath")
	private String thumbnailUploadPath;
	@Autowired FileUploadService fileUploadService;
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
	public String uploadForm(Model model, @RequestParam("username") String username, @RequestParam("file") MultipartFile file) throws Exception {
		 
		UUID uid = UUID.randomUUID();
		String fileName = file.getOriginalFilename();
		String fileId = uid.toString() + fileName;
		File imageFile = fileUploadService.uploadImageFile(imageUploadPath, fileId, file.getBytes(), fileName);
		fileUploadService.uploadThumbnailFile(thumbnailUploadPath, fileId, imageFile, fileName);
		fileUploadService.createFileData(username, fileId, fileName);
		model.addAttribute("savedImageName", fileName);

		return "imageFileUploadResult";
	}
	
	@RequestMapping(value = "/move/imageFileUpload")
	public String moveUpload(Model model, @RequestParam("username") String username) {
		model.addAttribute("username", username);
		return "imageFileUpload";
	}
}
