package com.swan.picturerepository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swan.picturerepository.dao.UserFileInfoDAO;
import com.swan.picturerepository.dto.UserFileInfoDTO;
import com.swan.picturerepository.model.BulletinBoard;

@Service
public class FileSearchService {

	@Autowired UserFileInfoDAO userFileInfoDAO;
	
	public List<BulletinBoard> getSearchBoardList(String strSearch, int page, int maxImageCnt) {		
		//List<UserFileInfo> fileList = userFileInfoDAO.selectFileName(strSearch);
		List<BulletinBoard> fileList = (List<BulletinBoard>)userFileInfoDAO.selectBoardByProcedure(strSearch, page, maxImageCnt);
		return fileList; 
	}
	
	public List<UserFileInfoDTO> getSearchFileListByFileId(String strbulletinId) {	
		
		List<UserFileInfoDTO> fileList = userFileInfoDAO.selectFileId(strbulletinId);
		
		return fileList; 
	}
	
	public int getSearchFileCnt(String strSearch) {
		int fileCnt = userFileInfoDAO.selectFileCntByProcedure(strSearch);
		return fileCnt;
	}
}
