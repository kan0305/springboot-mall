package com.example.springmall.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springmall.common.ResponseVO;
import com.example.springmall.common.ResponseVO.CodeType;
import com.example.springmall.dto.UserLoginRequest;
import com.example.springmall.dto.UserRegisterRequest;
import com.example.springmall.model.UserVO;
import com.example.springmall.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<ResponseVO> register(@RequestBody @Valid UserRegisterRequest request) {
		ResponseVO response = new ResponseVO();

		try {
			// 檢查email是否已註冊
			UserVO user = userService.getUserByEmail(request.getEmail());

			if (user != null) {
				logger.warn("該 Email [{}] 已註冊", request.getEmail());
				response.setRtnCode(CodeType.USER_SAME_EMAIL_FAIL.getCode());
				response.setRtnMsg(CodeType.USER_SAME_EMAIL_FAIL.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			// 檢查會員是否建立成功
			UserVO newUser = userService.register(request);

			if (newUser == null) {
				logger.warn("帳戶 [{}] 建立失敗", request.getEmail());
				response.setRtnCode(CodeType.USER_CREATE_FAIL.getCode());
				response.setRtnMsg(CodeType.USER_CREATE_FAIL.getMessage());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("user", newUser);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (Exception e) {
			logger.error("UserController [register] Error: {}", ExceptionUtils.getStackTrace(e));
			response.setRtnCode(CodeType.FAIL.getCode());
			response.setRtnMsg(CodeType.FAIL.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<ResponseVO> login(@RequestBody @Valid UserLoginRequest request) {
		ResponseVO response = new ResponseVO();

		try {
			UserVO user = userService.login(request);
			
			if(user == null) {
				logger.warn("會員登入失敗: email= {}, password= {}", request.getEmail(), request.getPassword());
				response.setRtnCode(CodeType.USER_LOGIN_FAIL.getCode());
				response.setRtnMsg(CodeType.USER_LOGIN_FAIL.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
			}
			
			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("user", user);

		} catch (Exception e) {
			logger.error("UserController [login] Error: {}", ExceptionUtils.getStackTrace(e));
			response.setRtnCode(CodeType.FAIL.getCode());
			response.setRtnMsg(CodeType.FAIL.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
