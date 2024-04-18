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
			Integer userId = userService.register(request);
			
			if(userId == -1) {
				response.setRtnCode(CodeType.FAIL.getCode());
				response.setRtnMsg(CodeType.FAIL.getMessage());
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			
			UserVO user = userService.getUserById(userId);			
			
			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("user", user);

		} catch (Exception e) {
			logger.error("UserController [register] Error: {}", ExceptionUtils.getStackTrace(e));
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
