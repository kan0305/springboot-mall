package com.example.springmall.dao;

import com.example.springmall.dto.UserRegisterRequest;
import com.example.springmall.model.UserVO;

public interface UserDao {
	UserVO getUserById(Integer userId);
	
	UserVO getUserByEmail(String email);
	
	Integer createUser(UserRegisterRequest request);
}
