package com.example.springmall.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserVO {
	private Integer userId;
	private String email;
	
	@JsonIgnore
	private String password;
	private Date createdDate;
	private Date lastModifiedDate;
}
