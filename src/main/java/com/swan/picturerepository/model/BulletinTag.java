package com.swan.picturerepository.model;

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
public class BulletinTag {
	@Id
	private Long bulletinId;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String tagId;
	@Column(length=10)
	private String tagName;
}
