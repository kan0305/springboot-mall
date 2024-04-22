package com.example.springmall.controller;

import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springmall.common.ResponseVO;
import com.example.springmall.common.ResponseVO.CodeType;
import com.example.springmall.dto.OrderCreateRequest;
import com.example.springmall.model.OrderVO;
import com.example.springmall.model.UserVO;
import com.example.springmall.service.OrderService;
import com.example.springmall.service.UserService;

@RestController
@RequestMapping("/users/{userId}/orders")
public class OrderController {
	private Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@PostMapping()
	public ResponseEntity<ResponseVO> createOrder(@PathVariable Integer userId,
			@RequestBody OrderCreateRequest request) {
		ResponseVO response = new ResponseVO();

		try {
			// 檢查用戶是否存在
			UserVO user = userService.getUserById(userId);

			if (user == null) {
				logger.warn("用戶{}不存在", userId);
				response.setRtnCode(CodeType.USER_NOT_FOUND.getCode());
				response.setRtnMsg(CodeType.USER_NOT_FOUND.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			// 創建訂單
			Map<String, Object> result = orderService.createOrder(userId, request);

			Integer orderId = (int) result.get("orderId");

			if (orderId == -1) {
				logger.info("訂單建立失敗, userId = {}", userId);
				
				response.setRtnCode(CodeType.ORDER_CREATE_FAIL.getCode());

				if (result.containsKey("msg")) {
					response.setRtnMsg(String.valueOf(result.get("msg")));
				} else {
					response.setRtnMsg(CodeType.ORDER_CREATE_FAIL.getMessage());
				}

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			// 取得訂單內容
			OrderVO order = orderService.getOrderById(orderId);

			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("order", order);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (Exception e) {
			logger.error("OrderController [createOrder] Error: {}", ExceptionUtils.getStackTrace(e));
			response.setRtnCode(CodeType.FAIL.getCode());
			response.setRtnMsg(CodeType.FAIL.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
}
