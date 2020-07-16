package com.swan.picturerepository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@Column(length=20, nullable = false)
	@NotEmpty(message = "아이디를 입력해주세요.")
	private String username;
	@Column(length=20, nullable = false)
	@NotEmpty(message = "비밀번호를 입력해주세요.")
	private String password;
	@Column(length=100, nullable = false)
	@Email
	@NotEmpty(message = "이메일을 입력해주세요.")
	private String email;
	@Column(length=50, nullable = true)
	private String authority;
	@Column(nullable = true)
	private int enabled;
}
