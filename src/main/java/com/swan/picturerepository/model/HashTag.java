package com.swan.picturerepository.model;

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
public class HashTag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tagId;
	@Column(length=10)
	private String tagName;
	@Column
	private Timestamp isrtDt;
}
