package com.swan.picturerepository.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
	@RequestMapping(value = "/async-like.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> updateLike(Model model, @RequestParam("username") String username, @RequestParam("fileId") String fileId) throws Exception {
		Map<String, String> map = new HashMap<>();
		
		map.put("like", "N");
		
		return map;
	}
}
