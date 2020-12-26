package com.swan.picturerepository.model;

import java.io.Serializable;
import java.time.LocalTime;

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
	@Column(length=20)
	private String username;
	@Id
	@Column(length=36)
	private String fileId;
	@Column
	private String fileName;
	@Column
	private LocalTime isrtDt;
}
