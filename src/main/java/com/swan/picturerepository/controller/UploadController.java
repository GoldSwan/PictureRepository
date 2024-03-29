package com.swan.picturerepository.controller;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swan.picturerepository.dto.UserFileInfoDTO;
import com.swan.picturerepository.service.BulletinboardService;
import com.swan.picturerepository.service.FileSearchService;
import com.swan.picturerepository.service.FileUploadService;
import com.swan.picturerepository.service.HashTagService;

@Controller
@RequestMapping("/bulletinboards")
public class UploadController {
	
	@Autowired BulletinboardService bulletinboardService;
	@Autowired FileUploadService fileUploadService;
	@Autowired FileSearchService fileSearchService;
	@Autowired HashTagService hashTagService;
	
	@PostMapping(value = "/newbulletinboard")
	public ModelAndView uploadFormMulti(Model model, HttpServletRequest req, @RequestParam("file") List<MultipartFile> fileList) throws Exception {
		
		String strBulletinId = "";
		String strUsername = "";
		String strTitle = "";
		String strContent = "";
		String strPublicRange = "";
		String strHashTagList = "";
		
		strUsername = req.getParameter("username");
		strTitle = req.getParameter("title");
		strContent = req.getParameter("content");
		strPublicRange = req.getParameter("publicRange");
		strHashTagList = req.getParameter("hashTagList");
		
		StringBuffer sb = new StringBuffer();
		ModelAndView mv = new ModelAndView();
		ArrayList<String> bulletinBoardInfoList = new ArrayList<>();	
		ArrayList<String> userFileInfoList = new ArrayList<>();
		Map<String, ArrayList<String>> mapHashTags = new Gson().fromJson(strHashTagList, new TypeToken<HashMap<String, ArrayList<String>>>() {}.getType());
		
		ArrayList<String> tagList = mapHashTags.get("tags");
		
		bulletinBoardInfoList.add(0, strContent);
		bulletinBoardInfoList.add(1, "N");
		bulletinBoardInfoList.add(2, strPublicRange);
		bulletinBoardInfoList.add(3, strTitle);
		bulletinBoardInfoList.add(4, strUsername);
		
		for (MultipartFile file : fileList) {
			if (file.isEmpty()) {
				model.addAttribute("uploadMultiErrorMsg", "선택한 파일이 없습니다.");
				mv.setViewName("board/imageFileUpload");				
				return mv;
			}
		}
		try {
			for (int i = 0 ; i < fileList.size() ; i++) {
				UUID uid = UUID.randomUUID();
				String strFileName = fileList.get(i).getOriginalFilename();
				String strFileExtension = FilenameUtils.getExtension(strFileName);
				String strFileId = String.format("%s.%s",uid.toString(),strFileExtension);
				File imageFile = fileUploadService.uploadImageFile(strFileId, fileList.get(i).getBytes(), strFileName);
				fileUploadService.uploadThumbnailFile(strFileId, imageFile, strFileName);
				fileUploadService.uploadFullThumbnailFile(strFileId, imageFile, strFileName);
				userFileInfoList.add(i,strFileId);		
			
				sb.append(strFileName).append(",");
			}
		
		strBulletinId = bulletinboardService.createBulletinboard(bulletinBoardInfoList, userFileInfoList, tagList);

		}catch(Exception e) {
			model.addAttribute("uploadMultiErrorMsg", e.getMessage());
			mv.setViewName("board/imageFileUpload");				
			return mv;
		}
		
		if(strBulletinId == "BulletinboardError") {
			model.addAttribute("uploadMultiErrorMsg", "업로드에서 에러가 발생했습니다.");
			mv.setViewName("board/imageFileUpload");				
			return mv;
		}

		if(strBulletinId == "HashTagError") {
			model.addAttribute("uploadMultiErrorMsg", "해시태그 생성에서 에러가 발생했습니다.");
			mv.setViewName("board/imageFileUpload");				
			return mv;			
		}
		
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(req.getContextPath()+"/bulletinboards/" + strBulletinId);
		mv.setView(redirectView);

		return mv;
	}

	@GetMapping(value = "/newbulletinboard/{bulletinId}")
	public ModelAndView selectBulletinboard(Model model, HttpServletRequest req, @PathVariable("bulletinId") String strbulletinId, Principal principal) throws Exception {
		ModelAndView mv = new ModelAndView();	
		RedirectView redirectView = new RedirectView();
		List<UserFileInfoDTO> fileList = null;
		List<String> tagList = null;
		String strTitle = "";
		String strUsername = "";
		String strContent = "";		
		String strSearchDataMap = "";

		//권한 체크
		if(!bulletinboardService.isBulletinboardAuth(strbulletinId, principal.getName())){
			model.addAttribute("deleteErrorMsg", "수정 권한이 없습니다.");
			redirectView.setUrl(req.getContextPath()+"/bulletinboards/" + strbulletinId);
			mv.setView(redirectView);
			return mv;
		}
		
		fileList = fileSearchService.getSearchFileListByFileId(strbulletinId);
		List<Map<String, String>> listImages = new ArrayList<>();
		List<Map<String, String>> listTags = new ArrayList<>();
		
		if(fileList!=null && fileList.size()>0) {
			strTitle = fileList.get(0).getTitle();
			strUsername = fileList.get(0).getUsername();
			strContent = fileList.get(0).getContent();

			for(int i = 0; i<fileList.size();i++) {
				Map<String, String> map = new HashMap<>();				
				map.put("image", fileList.get(i).getFileId());
				listImages.add(map);
			}
		}
		
		tagList = hashTagService.getSearchTagList(strbulletinId);
		
		if(tagList!=null && tagList.size()>0) {
			for(String strTagName : tagList) {
				Map<String, String> map = new HashMap<>();
				map.put("tag", strTagName);
				listTags.add(map);
			}
		}
		
		Map<String, Object> searchDataMap = new HashMap<>();	
		searchDataMap.put("imageData", listImages);
		searchDataMap.put("tagData", listTags);
		strSearchDataMap = new Gson().toJson(searchDataMap);
		
		mv.addObject("searchDataMap",strSearchDataMap);
		mv.addObject("title",strTitle);
		mv.addObject("username",strUsername);
		mv.addObject("content",strContent);
		mv.addObject("bulletinId", strbulletinId);
		
		mv.setViewName("board/imageFileUpload");
		return mv;
	}
	
	@PostMapping(value = "/newbulletinboard/{bulletinId}")
	public ModelAndView updateBulletinboard(Model model, HttpServletRequest req, @RequestParam("file") List<MultipartFile> fileList, @PathVariable("bulletinId") String strBulletinId) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		RedirectView redirectView = new RedirectView();
		String strUsername = "";
		String strTitle = "";
		String strContent = "";
		String strPublicRange = "";
		String strRemoveImageList = "";
		String strHashTagList = "";
		String strRemoveHashTagList = "";
		List<UserFileInfoDTO> savedFileList = new ArrayList<UserFileInfoDTO>();	
		
		strUsername = req.getParameter("username");
		strTitle = req.getParameter("title");
		strContent = req.getParameter("content");
		strPublicRange = req.getParameter("publicRange");
		strRemoveImageList = req.getParameter("removeImageList");
		strHashTagList = req.getParameter("hashTagList");
		strRemoveHashTagList = req.getParameter("removeHashTagList");
		
		Map<String, ArrayList<String>> mapRemoveImages = new Gson().fromJson(strRemoveImageList, new TypeToken<HashMap<String, ArrayList<String>>>() {}.getType());
		Map<String, ArrayList<String>> mapHashTags = new Gson().fromJson(strHashTagList, new TypeToken<HashMap<String, ArrayList<String>>>() {}.getType());
		Map<String, ArrayList<String>> mapRemoveHashTags  = new Gson().fromJson(strRemoveHashTagList, new TypeToken<HashMap<String, ArrayList<String>>>() {}.getType());
		
		List<String> arrImages = mapRemoveImages.get("images");
		for(String strFileId : arrImages) {
			savedFileList.add(new UserFileInfoDTO.Builder("", "", "", "", strFileId, "", "").build());
		}
		
		ArrayList<String> tagList = mapHashTags.get("tags");
		ArrayList<String> removeTagList = mapRemoveHashTags.get("tags");
		
		if(savedFileList.size() > 0) {
			if(!fileUploadService.deleteImageFile(savedFileList) || !fileUploadService.deleteThumbnailFile(savedFileList) || !fileUploadService.deleteFullThumbnailFile(savedFileList)) {
				model.addAttribute("deleteErrorMsg", "파일 삭제에서 에러가 발생했습니다.");
				redirectView.setUrl(req.getContextPath()+"/bulletinboards/" + strBulletinId);
				mv.setView(redirectView);
				return mv;
			}
			if(!fileUploadService.deleteFileInfo(savedFileList)) {
				model.addAttribute("deleteErrorMsg", "데이터 삭제에서 에러가 발생했습니다.");
				redirectView.setUrl(req.getContextPath()+"/bulletinboards/" + strBulletinId);
				mv.setView(redirectView);
				return mv;
			}
		}
		
		StringBuffer sb = new StringBuffer();
		ArrayList<String> bulletinBoardInfoList = new ArrayList<>();	
		ArrayList<String> userFileInfoList = new ArrayList<>();
		
		bulletinBoardInfoList.add(0, strContent);
		bulletinBoardInfoList.add(1, "N");
		bulletinBoardInfoList.add(2, strPublicRange);
		bulletinBoardInfoList.add(3, strTitle);
		bulletinBoardInfoList.add(4, strUsername);
		
		try {
			//신규 파일 리스트
			for (int i = 0 ; i < fileList.size() ; i++) {
				if(!fileList.get(i).isEmpty()) {
					UUID uid = UUID.randomUUID();
					String strFileName = fileList.get(i).getOriginalFilename();
					String strFileExtension = FilenameUtils.getExtension(strFileName);
					String strFileId = String.format("%s.%s",uid.toString(),strFileExtension);
					File imageFile = fileUploadService.uploadImageFile(strFileId, fileList.get(i).getBytes(), strFileName);
					fileUploadService.uploadThumbnailFile(strFileId, imageFile, strFileName);
					fileUploadService.uploadFullThumbnailFile(strFileId, imageFile, strFileName);
					userFileInfoList.add(i,strFileId);		
					sb.append(strFileName).append(",");
				}
			}
			
			if(!bulletinboardService.updateBulletinboard(bulletinBoardInfoList, userFileInfoList, strBulletinId)) {
				model.addAttribute("uploadMultiErrorMsg", "업로드에서 에러가 발생했습니다.");
				mv.setViewName("board/imageFileUpload");				
				return mv;
			}
		
			//해시 태그 삭제
			if(!hashTagService.deleteHashTag(strBulletinId, removeTagList)) {
				model.addAttribute("uploadMultiErrorMsg", "해시태그 삭제에서 에러가 발생했습니다.");
				mv.setViewName("board/imageFileUpload");				
				return mv;
			}
			//해시 태그 생성
			if(!hashTagService.createHashTag(strBulletinId, tagList)) {
				model.addAttribute("uploadMultiErrorMsg", "해시태그 생성에서 에러가 발생했습니다.");
				mv.setViewName("board/imageFileUpload");				
				return mv;
			}
		}catch(Exception e) {
			model.addAttribute("uploadMultiErrorMsg", e.getMessage());
			mv.setViewName("board/imageFileUpload");				
			return mv;
		}		
		
		redirectView.setUrl(req.getContextPath()+"/bulletinboards/" + strBulletinId);
		mv.setView(redirectView);
		
		return mv;
	}
	
	@DeleteMapping(value = "/newbulletinboard/{bulletinId}")
	public ModelAndView deleteBulletinboard(Model model, HttpServletRequest req, @PathVariable("bulletinId") String strBulletinId, Principal principal) throws Exception {
		ModelAndView mv = new ModelAndView();
		RedirectView redirectView = new RedirectView();
		List<UserFileInfoDTO> fileList = null;	
		String strRemoveHashTagList = "";		
		
		//권한 체크
		if(!bulletinboardService.isBulletinboardAuth(strBulletinId, principal.getName())){
			model.addAttribute("deleteErrorMsg", "삭제 권한이 없습니다.");
			redirectView.setUrl(req.getContextPath()+"/bulletinboards/" + strBulletinId);
			mv.setView(redirectView);
			return mv;
		}
		
		fileList = fileSearchService.getSearchFileListByFileId(strBulletinId);
		if(!fileUploadService.deleteImageFile(fileList) || !fileUploadService.deleteThumbnailFile(fileList) || !fileUploadService.deleteFullThumbnailFile(fileList)) {
			model.addAttribute("deleteErrorMsg", "파일 삭제에서 에러가 발생했습니다.");
			redirectView.setUrl(req.getContextPath()+"/bulletinboards/" + strBulletinId);
			mv.setView(redirectView);
			return mv;
		}
	
		//해시 태그 삭제
		strRemoveHashTagList = req.getParameter("removeHashTagList");
		Map<String, ArrayList<String>> mapRemoveHashTags  = new Gson().fromJson(strRemoveHashTagList, new TypeToken<HashMap<String, ArrayList<String>>>() {}.getType());
		ArrayList<String> removeTagList = mapRemoveHashTags.get("tags");
		
		if(!hashTagService.deleteHashTag(strBulletinId, removeTagList)) {
			model.addAttribute("deleteErrorMsg", "해시태그 삭제에서 에러가 발생했습니다.");
			redirectView.setUrl(req.getContextPath()+"/bulletinboards/" + strBulletinId);				
			return mv;
		}
		
		if(!bulletinboardService.deleteBulletinboard(strBulletinId)) {
			model.addAttribute("deleteErrorMsg", "데이터 삭제에서 에러가 발생했습니다.");
			redirectView.setUrl(req.getContextPath()+"/bulletinboards/" + strBulletinId);
			mv.setView(redirectView);
			return mv;
		}
		
		redirectView.setUrl(req.getContextPath()+"/bulletinboards?search=&page=1");
		mv.setView(redirectView);
		return mv;
	}
	
	@GetMapping(value = "/newbulletinboard")
	public ModelAndView moveUpload(Model model, @RequestParam("username") String username) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/imageFileUpload");
		model.addAttribute("username", username);	
		return mv;
	}
}
