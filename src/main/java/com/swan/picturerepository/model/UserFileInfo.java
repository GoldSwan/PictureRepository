package com.swan.picturerepository.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	private Long bulletinId;
	@Id
	@Column(length=50)
	private String fileId;
	@Column
	private Timestamp isrtDt;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="bulletinId")
	private BulletinBoard bulletinBoard;
}
