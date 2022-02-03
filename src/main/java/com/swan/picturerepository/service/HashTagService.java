package com.swan.picturerepository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swan.picturerepository.dao.HashTagDAO;

@Service
public class HashTagService {
	@Autowired HashTagDAO hashTagDAO;
	
	public boolean createHashTag(String strBulletinId, List<String> tagList) {
		List<String> listHashTagId = hashTagDAO.createHashTagInfo(tagList);
		return hashTagDAO.createBoardTagRelationInfo(strBulletinId, listHashTagId);
	}

	public boolean deleteHashTag(String strBulletinId, List<String> tagList) {
		if(hashTagDAO.deleteBoardTagRelation(strBulletinId, tagList)) {
			for(String strTagName : tagList) {
				List<String> listRemoveHashTagId = hashTagDAO.selectCanDeleteTagId(strTagName, strBulletinId);
				if(listRemoveHashTagId!=null && listRemoveHashTagId.size()>0) {
					if(!hashTagDAO.deleteHashTag(listRemoveHashTagId))
						return false;
				}
			}
		}
		return true;
	}
	
	public List<String> getSearchTagList(String strBulletinId){
		return hashTagDAO.selectTagList(strBulletinId);
	}	
}
