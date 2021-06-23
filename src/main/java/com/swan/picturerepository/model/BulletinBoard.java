package com.swan.picturerepository.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class BulletinBoard implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long bulletinId;
	@Column(length=36)
	private String title;
	@Column(length=500)
	private String content;
	@Column(length=1)//A : all, C : Close
	private String publicRange;	
	@Column(length=1)
	private String likeFlag;
	@Column
	private long likeCnt;	
	@Column(length=20)
	private String username;		
	@Column
	private Timestamp isrtDt;
	@Column(length=50)
	private String representativeFileId;//대표 파일 아이디	
}
