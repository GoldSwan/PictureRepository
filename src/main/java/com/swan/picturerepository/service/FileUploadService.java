package com.swan.picturerepository.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.swan.picturerepository.dto.UserFileInfoDTO;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class FileUploadService {

	static final int THUMB_WIDTH = 346;
	static final int THUMB_HEIGHT = 440;
	static final int FULL_THUMB_WIDTH = 692;
	static final int FULL_THUMB_HEIGHT = 880;
	@Resource(name = "imageUploadPath")
	private String imageUploadPath;
	@Resource(name = "thumbnailUploadPath")
	private String thumbnailUploadPath;
	@Resource(name = "fullUploadPath")
	private String fullUploadPath;
	
	public File uploadImageFile(String savedName, byte[] fileData, String fileName) throws Exception {
		File imageFile = new File(imageUploadPath, savedName);
		FileCopyUtils.copy(fileData, imageFile);
		return imageFile;
	}
	
	public void uploadThumbnailFile(String savedName, File imageFile, String fileName) throws Exception {
		File imageThumbnailFile = new File(thumbnailUploadPath, savedName);
		if (imageFile.exists()) {
			imageThumbnailFile.getParentFile().mkdirs();
			//Thumbnails.of(imageFile).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(imageThumbnailFile);//사진 원본 비율 그대로 썸네일 생성. 지정 크기가 원본 비율이 안된다면 자동 조정
			Thumbnails.of(imageFile).forceSize(THUMB_WIDTH, THUMB_HEIGHT).toFile(imageThumbnailFile);//사진 사이즈를 지정한 크기로만 썸네일 생성
		}
	}
	
	public void uploadFullThumbnailFile(String savedName, File imageFile, String fileName) throws Exception {
		File imageFullThumbnailFile = new File(fullUploadPath, savedName);
		if (imageFile.exists()) {
			imageFullThumbnailFile.getParentFile().mkdirs();
			Thumbnails.of(imageFile).forceSize(FULL_THUMB_WIDTH, FULL_THUMB_HEIGHT).toFile(imageFullThumbnailFile);//게시판용 썸네일 생성
		}
	}
	
	public boolean deleteImageFile(List<UserFileInfoDTO> fileList) {
		for(UserFileInfoDTO userFileInfoDTO : fileList) {
			File imageFile = new File(imageUploadPath, userFileInfoDTO.getFileId());
			if(imageFile.exists()) {
				if(!imageFile.delete())
					return false;
			}
		}
		return true;
	}
	
	public boolean deleteThumbnailFile(List<UserFileInfoDTO> fileList) {
		for(UserFileInfoDTO userFileInfoDTO : fileList) {
			File imageFile = new File(thumbnailUploadPath, userFileInfoDTO.getFileId());
			if(imageFile.exists()) {
				if(!imageFile.delete())
					return false;
			}
		}
		return true;
	}
	
	public boolean deleteFullThumbnailFile(List<UserFileInfoDTO> fileList) {
		for(UserFileInfoDTO userFileInfoDTO : fileList) {
			File imageFile = new File(fullUploadPath, userFileInfoDTO.getFileId());
			if(imageFile.exists()) {
				if(!imageFile.delete())
					return false;
			}
		}
		return true;
	}
}
