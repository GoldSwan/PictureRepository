package com.swan.picturerepository.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swan.picturerepository.service.LikeService;

@Controller
public class LikeController {
	@Autowired
	LikeService likeService;
	
	@RequestMapping(value = "/async-like.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> updateLike(Model model, @RequestParam("username") String username, @RequestParam("fileId") String fileId) throws Exception {
		Map<String, String> map = new HashMap<>();
		String likeFlag = likeService.updateLike(username, fileId);
		map.put("like", likeFlag);
		
		return map;
	}
}
