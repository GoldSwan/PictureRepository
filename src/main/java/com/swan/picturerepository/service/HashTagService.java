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
	public List<String> createHashTag(List<String> arrTags) {
		return hashTagDAO.createHashTagInfo(arrTags);
	}
}
