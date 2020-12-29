package com.swan.picturerepository.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import com.swan.picturerepository.dao.UserFileInfoDAO;

@Service
public class FileUploadService {
	
	@Autowired UserFileInfoDAO userFileInfoDAO;
	
	public String uploadFile(String uploadPath, byte[] fileData, String fileName) throws Exception {
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + fileName;
		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(fileData, target);
		return savedName;
	}
	
	public void createFileData(String username, String fileId, String fileName) {
		userFileInfoDAO.insertUserFileInfo(username, fileId, fileName);
	}
}
