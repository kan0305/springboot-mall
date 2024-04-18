package com.example.springmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springmall.dao.UserDao;
import com.example.springmall.dto.UserRegisterRequest;
import com.example.springmall.model.UserVO;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public Integer register(UserRegisterRequest request) {
		return userDao.createUser(request);
	}
	
	public UserVO getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}
}
