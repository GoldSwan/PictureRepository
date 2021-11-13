package com.swan.picturerepository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swan.picturerepository.dao.BulletinboardDAO;

@Service
public class LikeService {
	@Autowired
	BulletinboardDAO bulletinboardDAO;
	List<String> likeList = null;
	
	public String updateLike(String fileId) {
		bulletinboardDAO.updateLikeFlag(fileId);
		likeList =  bulletinboardDAO.selectLikeFlag(fileId);
		return likeList.size() > 0 ? likeList.get(0) : "N";
	}
}
