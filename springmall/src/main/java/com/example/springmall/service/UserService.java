package com.example.springmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springmall.dao.UserDao;
import com.example.springmall.dto.UserLoginRequest;
import com.example.springmall.dto.UserRegisterRequest;
import com.example.springmall.model.UserVO;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public UserVO register(UserRegisterRequest request) {

		Integer userId = userDao.createUser(request);

		if (userId == -1) {
			return null;
		}

		return getUserById(userId);
	}

	public UserVO login(UserLoginRequest request) {
		UserVO user = getUserByEmail(request.getEmail());
		
		if(user == null) {
			return null;
		}
		
		if(!request.getPassword().equals(user.getPassword())){
			return null;
		}
		
		return user;		
	}

	public UserVO getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}

	public UserVO getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

}
