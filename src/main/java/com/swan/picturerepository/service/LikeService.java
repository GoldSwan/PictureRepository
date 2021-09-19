package com.swan.picturerepository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swan.picturerepository.dao.UserFileInfoDAO;

@Service
public class LikeService {
	@Autowired
	UserFileInfoDAO userFileInfoDAO;
	List<String> likeList = null;
	
	public String updateLike(String fileId) {
		userFileInfoDAO.updateLikeFlag(fileId);
		likeList =  userFileInfoDAO.selectLikeFlag(fileId);
		return likeList.size() > 0 ? likeList.get(0) : "N";
	}
}
