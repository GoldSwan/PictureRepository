package com.swan.picturerepository.controller;

import java.io.File;
import java.util.List;
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
		 
		if(file.isEmpty()) {
			model.addAttribute("uploadErrorMsg", "선택한 파일이 없습니다.");
			return "imageFileUpload";
		}
		UUID uid = UUID.randomUUID();
		String fileName = file.getOriginalFilename();
		String fileId = uid.toString() + fileName;
		File imageFile = fileUploadService.uploadImageFile(imageUploadPath, fileId, file.getBytes(), fileName);
		fileUploadService.uploadThumbnailFile(thumbnailUploadPath, fileId, imageFile, fileName);
		fileUploadService.createFileData(username, fileId, fileName);
		model.addAttribute("savedImageName", fileName);

		return "imageFileUploadResult";
	}

	@RequestMapping(value = "/uploadForm/multi", method = RequestMethod.POST)
	public String uploadFormMulti(Model model, @RequestParam("username") String username, @RequestParam("file") List<MultipartFile> fileList) throws Exception {
		for (MultipartFile file : fileList) {
			if (file.isEmpty()) {
				model.addAttribute("uploadMultiErrorMsg", "선택한 파일이 없습니다.");
				return "imageFileUpload";
			}
		}

		for (MultipartFile file : fileList) {
			UUID uid = UUID.randomUUID();
			String fileName = file.getOriginalFilename();
			String fileId = uid.toString() + fileName;
			File imageFile = fileUploadService.uploadImageFile(imageUploadPath, fileId, file.getBytes(), fileName);
			fileUploadService.uploadThumbnailFile(thumbnailUploadPath, fileId, imageFile, fileName);
			fileUploadService.createFileData(username, fileId, fileName);
			model.addAttribute("savedImageName", fileName);
		}
		return "imageFileUploadResult";
	}
	
	@RequestMapping(value = "/move/imageFileUpload")
	public String moveUpload(Model model, @RequestParam("username") String username) {
		model.addAttribute("username", username);
		return "imageFileUpload";
	}
}
