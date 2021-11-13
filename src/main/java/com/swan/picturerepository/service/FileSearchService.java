package com.swan.picturerepository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swan.picturerepository.dao.UserFileInfoDAO;
import com.swan.picturerepository.dto.UserFileInfoDTO;

@Service
public class FileSearchService {

	@Autowired UserFileInfoDAO userFileInfoDAO;
	
	public List<UserFileInfoDTO> getSearchFileListByFileId(String strbulletinId) {	
		
		List<UserFileInfoDTO> fileList = userFileInfoDAO.selectFileId(strbulletinId);
		
		return fileList; 
	}
}
