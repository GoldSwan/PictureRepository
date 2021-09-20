package com.swan.picturerepository.service;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import com.swan.picturerepository.dao.UserFileInfoDAO;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class FileUploadService {

	@Autowired
	UserFileInfoDAO userFileInfoDAO;
	static final int THUMB_WIDTH = 346;
	static final int THUMB_HEIGHT = 440;
	static final int FULL_THUMB_WIDTH = 692;
	static final int FULL_THUMB_HEIGHT = 880;
	public File uploadImageFile(String imageUploadPath, String savedName, byte[] fileData, String fileName) throws Exception {
		File imageFile = new File(imageUploadPath, savedName);
		FileCopyUtils.copy(fileData, imageFile);
		return imageFile;
	}
	
	public void uploadThumbnailFile(String uploadPath, String savedName, File imageFile, String fileName) throws Exception {
		File imageThumbnailFile = new File(uploadPath, savedName);
		if (imageFile.exists()) {
			imageThumbnailFile.getParentFile().mkdirs();
			//Thumbnails.of(imageFile).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(imageThumbnailFile);//사진 원본 비율 그대로 썸네일 생성. 지정 크기가 원본 비율이 안된다면 자동 조정
			Thumbnails.of(imageFile).forceSize(THUMB_WIDTH, THUMB_HEIGHT).toFile(imageThumbnailFile);//사진 사이즈를 지정한 크기로만 썸네일 생성
		}
	}
	
	public void uploadFullThumbnailFile(String uploadPath, String savedName, File imageFile, String fileName) throws Exception {
		File imageFullThumbnailFile = new File(uploadPath, savedName);
		if (imageFile.exists()) {
			imageFullThumbnailFile.getParentFile().mkdirs();
			Thumbnails.of(imageFile).forceSize(FULL_THUMB_WIDTH, FULL_THUMB_HEIGHT).toFile(imageFullThumbnailFile);//게시판용 썸네일 생성
		}
	}
	
	@Transactional
	public void createFileData(ArrayList<String> bulletinBoardInfoList, ArrayList<String> userFileInfoList) {
		userFileInfoDAO.insertUserFileInfo(bulletinBoardInfoList, userFileInfoList);
	}
}
