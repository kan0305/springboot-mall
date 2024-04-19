package com.example.springmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.example.springmall.dao.UserDao;
import com.example.springmall.dto.UserLoginRequest;
import com.example.springmall.dto.UserRegisterRequest;
import com.example.springmall.model.UserVO;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public UserVO register(UserRegisterRequest request) {
		
		// 使用 MD5 生成密碼的雜湊值
		request.setPassword(makeMD5(request.getPassword().getBytes()));

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
		
		// 使用 MD5 生成密碼的雜湊值
		String hashPassword = makeMD5(request.getPassword().getBytes());
		
		if(!hashPassword.equals(user.getPassword())){
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
	
	private String makeMD5(byte[] bytes) {
		return DigestUtils.md5DigestAsHex(bytes);
	}

}
