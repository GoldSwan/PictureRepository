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
	
	public String updateLike(String username, String fileId) {
		userFileInfoDAO.updateLikeFlag(username, fileId);
		likeList =  userFileInfoDAO.selectLikeFlag(username, fileId);
		return likeList.size() > 0 ? likeList.get(0) : "N";
	}
}
