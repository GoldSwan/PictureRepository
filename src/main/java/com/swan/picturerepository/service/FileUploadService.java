package com.swan.picturerepository.service;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import com.swan.picturerepository.dao.UserFileInfoDAO;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class FileUploadService {

	@Autowired
	UserFileInfoDAO userFileInfoDAO;
	static final int THUMB_WIDTH = 346;
	static final int THUMB_HEIGHT = 440;
	
	public File uploadImageFile(String imageUploadPath, String savedName, byte[] fileData, String fileName) throws Exception {
		File imageFile = new File(imageUploadPath, savedName);
		FileCopyUtils.copy(fileData, imageFile);
		return imageFile;
	}
	
	public void uploadThumbnailFile(String uploadPath, String savedName, File imageFile, String fileName) throws Exception {
		File imageThumbnailFile = new File(uploadPath, savedName);
		if (imageFile.exists()) {
			imageThumbnailFile.getParentFile().mkdirs();
			Thumbnails.of(imageFile).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(imageThumbnailFile);
		}
	}
	
	public void createFileData(String username, String fileId, String fileName) {
		userFileInfoDAO.insertUserFileInfo(username, fileId, fileName);
	}
}
