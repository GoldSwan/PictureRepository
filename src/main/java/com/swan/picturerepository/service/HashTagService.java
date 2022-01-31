package com.swan.picturerepository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swan.picturerepository.dao.HashTagDAO;

@Service
public class HashTagService {
	@Autowired HashTagDAO hashTagDAO;
	
	@Transactional
	public boolean createHashTag(String strBulletinId, List<String> arrTags) {
		List<String> listHashTagId = hashTagDAO.createHashTagInfo(arrTags);
		return hashTagDAO.createBoardTagRelationInfo(strBulletinId, listHashTagId);
	}
	
	public List<String> getSearchTagList(String strBulletinId){
		return hashTagDAO.selectTagList(strBulletinId);
	}
}
