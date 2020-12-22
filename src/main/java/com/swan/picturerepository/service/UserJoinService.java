package com.swan.picturerepository.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swan.picturerepository.dao.UserDAO;
import com.swan.picturerepository.model.User;

@Service
public class UserJoinService {

	@Autowired
	private UserDAO userDAO;
	
	public void createUser(User user) {
		userDAO.insertUser(user);
	}
	
	public boolean reConfirmPassword(String password, String confirmPassword) {	
		return password.equals(confirmPassword) ? true : false; 
	}
	
	public boolean isDuplicateUsername(String username) {
		return userDAO.isExistUserName(username) ? true : false;
	}
}
