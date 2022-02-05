package com.swan.picturerepository.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swan.picturerepository.dao.BulletinboardDAO;
import com.swan.picturerepository.dto.BulletinBoardDTO;
import com.swan.picturerepository.dto.UserFileInfoDTO;

@Service
public class BulletinboardService {

	@Autowired BulletinboardDAO bulletinboardDAO;
	@Autowired HashTagService hashTagService;
	
	public List<BulletinBoardDTO> getSearchBoardList(String strSearchType, String strSearch, int page, int maxImageCnt) {		
		List<BulletinBoardDTO> fileList = (List<BulletinBoardDTO>)bulletinboardDAO.selectBoardInfo(strSearchType, strSearch, page, maxImageCnt);
		return fileList; 
	}
	
	public List<UserFileInfoDTO> getSearchFileListBybulletinId(String strbulletinId) {	
		
		List<UserFileInfoDTO> fileList = bulletinboardDAO.selectFileId(strbulletinId);
		
		return fileList; 
	}
	
	public int getSearchBulletinboardCnt(String strSearchType, String strSearch) {
		int fileCnt = bulletinboardDAO.selectBulletinboardCnt(strSearchType, strSearch);
		return fileCnt;
	}
	
	public String createBulletinboard(ArrayList<String> bulletinBoardInfoList, ArrayList<String> userFileInfoList, ArrayList<String> tagList) {
		String strBulletinId = "";

		strBulletinId = bulletinboardDAO.insertBulletinboardInfo(bulletinBoardInfoList, userFileInfoList);
		
		if(strBulletinId == "") return "BulletinboardError";
		
		if(!hashTagService.createHashTag(strBulletinId, tagList)) {
			return "HashTagError";
		}

		return strBulletinId;
	}
	
	public boolean updateBulletinboard(ArrayList<String> bulletinBoardInfoList, ArrayList<String> userFileInfoList, String strBulletinId) {
		return bulletinboardDAO.updateBulletinboardInfo(bulletinBoardInfoList, userFileInfoList, strBulletinId);
	}
	
	public boolean deleteBulletinboard(String strBulletinId) {
		return bulletinboardDAO.deleteBulletinboardInfo(strBulletinId) && bulletinboardDAO.deleteUserFileInfo(strBulletinId);
	}
	//게시판 수정, 삭제 권한 확인
	public boolean isBulletinboardAuth(String strBulletinId, String strUsername) {
		int numAuth = bulletinboardDAO.selectBulletinboardAuth(strBulletinId, strUsername);
		return (numAuth == 0) ? false : true;
	}
}
