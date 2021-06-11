package com.swan.picturerepository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swan.picturerepository.dao.UserFileInfoDAO;
import com.swan.picturerepository.model.UserFileInfo;

@Service
public class FileSearchService {

	List<UserFileInfo> fileList = null;
	@Autowired UserFileInfoDAO userFileInfoDAO;
	
	public List<UserFileInfo> getSearchFileList(String strSearch, int page, int maxPage) {		
		//List<UserFileInfo> fileList = userFileInfoDAO.selectFileName(strSearch);
		List<UserFileInfo> fileList = (List<UserFileInfo>)userFileInfoDAO.selectFileNameByProcedure(strSearch, page, maxPage);
		return fileList; 
	}
	
	public List<UserFileInfo> getSearchFileListByFileId(String strFileId) {	
		
		List<UserFileInfo> fileList = userFileInfoDAO.selectFileId(strFileId);
		
		return fileList; 
	}
}
