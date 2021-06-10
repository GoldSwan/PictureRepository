package com.swan.picturerepository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserFileTag {
	@Id
	@Column(length=36)
	private String fileId;
	@Column(length=10)
	private String tag;
}