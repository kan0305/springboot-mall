package com.example.springmall.model;

import java.util.Date;

import lombok.Data;

@Data
public class UserVO {
	private Integer userId;
	private String email;
	private String password;
	private Date createdDate;
	private Date lastModifiedDate;
}
