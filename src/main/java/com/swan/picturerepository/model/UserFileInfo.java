package com.swan.picturerepository.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class UserFileInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(length=50)
	private String fileId;
	@Column(length=20)
	private String username;	
	@Column
	private String fileName;
	@Column
	private LocalDate isrtDt;
	@Column(length=1)
	private String likeFlag;
	@Column
	private long likeCnt;
	@Column(length=36)
	private String title;
	@Column(length=500)
	private String content;
	@Column
	private String tag;
	@Column(length=1)//A : all, C : Close
	private String publicRange;
}
