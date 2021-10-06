package com.swan.picturerepository.dto;

import lombok.Getter;

@Getter
public class UserFileInfoDTO {
	private final String title;//제목
	private final String isrtDt;//작성시간
	private final String username;//작성자
	private final String content;//내용
	private final String fileId;//사진ID	
	private final String likeCnt;//좋아요 횟수
	private final String likeFlag;//좋아요 여부
	
	public static class Builder {
		//필수 매개변수
		private final String title;//제목
		private final String isrtDt;//작성시간
		private final String username;//작성자
		private final String content;//내용
		private final String fileId;//사진ID	
		private final String likeCnt;//좋아요 횟수
		private final String likeFlag;//좋아요 여부
		public Builder(String title, String isrtDt, String username, String content, String fileId, String likeCnt, String likeFlag) {
			this.title = title;
		    this.isrtDt = isrtDt;
		    this.username = username;
		    this.content = content;
		    this.fileId = fileId;
		    this.likeCnt = likeCnt;
		    this.likeFlag = likeFlag;
		}
		
		public UserFileInfoDTO build() {
			return new UserFileInfoDTO(this);
		}
	}
	
	private UserFileInfoDTO(Builder builder) {
		this.title = builder.title;
	    this.isrtDt = builder.isrtDt;
	    this.username = builder.username;
	    this.content = builder.content;
	    this.fileId = builder.fileId;
	    this.likeCnt = builder.likeCnt;
	    this.likeFlag = builder.likeFlag;
	}
}
