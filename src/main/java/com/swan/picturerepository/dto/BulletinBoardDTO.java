package com.swan.picturerepository.dto;

import lombok.Getter;

@Getter
public class BulletinBoardDTO {
	private final String bulletinId;//게시판ID
	private final String representativeFileId;//대표사진ID
	private final String likeFlag;//좋아요 여부

	public static class Builder {
		private final String bulletinId;//게시판ID
		private final String representativeFileId;//대표사진ID
		private final String likeFlag;//좋아요 여부

		public Builder(String bulletinId, String representativeFileId, String likeFlag) {
			this.bulletinId = bulletinId;
		  this.representativeFileId = representativeFileId;
		  this.likeFlag = likeFlag;
		}
		
		public BulletinBoardDTO build() {
			return new BulletinBoardDTO(this);
		}
	}

	private BulletinBoardDTO(Builder builder) {
		this.bulletinId = builder.bulletinId;
	  this.representativeFileId = builder.representativeFileId;
	  this.likeFlag = builder.likeFlag;
	}
}
