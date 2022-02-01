package com.swan.picturerepository.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swan.picturerepository.dao.BulletinboardDAO;
import com.swan.picturerepository.dto.UserFileInfoDTO;
import com.swan.picturerepository.model.BulletinBoard;

@Service
public class BulletinboardService {

	@Autowired BulletinboardDAO bulletinboardDAO;
	@Autowired HashTagService hashTagService;
	
	public List<BulletinBoard> getSearchBoardList(String strSearch, int page, int maxImageCnt) {		
		//List<UserFileInfo> fileList = userFileInfoDAO.selectFileName(strSearch);
		List<BulletinBoard> fileList = (List<BulletinBoard>)bulletinboardDAO.selectBoardByProcedure(strSearch, page, maxImageCnt);
		return fileList; 
	}
	
	public List<UserFileInfoDTO> getSearchFileListBybulletinId(String strbulletinId) {	
		
		List<UserFileInfoDTO> fileList = bulletinboardDAO.selectFileId(strbulletinId);
		
		return fileList; 
	}
	
	public int getSearchBulletinboardCnt(String strSearch) {
		int fileCnt = bulletinboardDAO.selectBulletinboardCnt(strSearch);
		return fileCnt;
	}
	
	@Transactional
	public String createBulletinboard(ArrayList<String> bulletinBoardInfoList, ArrayList<String> userFileInfoList) {
		return bulletinboardDAO.insertBulletinboardInfo(bulletinBoardInfoList, userFileInfoList);
	}
	
	@Transactional
	public boolean updateBulletinboard(ArrayList<String> bulletinBoardInfoList, ArrayList<String> userFileInfoList, String strBulletinId) {
		return bulletinboardDAO.updateBulletinboardInfo(bulletinBoardInfoList, userFileInfoList, strBulletinId);
	}
	
	@Transactional
	public boolean deleteBulletinboard(String strBulletinId) {
		return bulletinboardDAO.deleteBulletinboardInfo(strBulletinId) && bulletinboardDAO.deleteUserFileInfo(strBulletinId);
	}
}
