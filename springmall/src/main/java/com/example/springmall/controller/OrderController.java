package com.example.springmall.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springmall.common.ResponseVO;
import com.example.springmall.common.ResponseVO.CodeType;
import com.example.springmall.dto.OrderCreateRequest;
import com.example.springmall.dto.OrderQueryParams;
import com.example.springmall.model.OrderVO;
import com.example.springmall.model.UserVO;
import com.example.springmall.service.OrderService;
import com.example.springmall.service.UserService;
import com.example.springmall.util.Page;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/users/{userId}/orders")
public class OrderController {
	private Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;
	
	@GetMapping()
	public ResponseEntity<ResponseVO> getOrders(
			// 查詢條件 Filtering
			@PathVariable Integer userId,
			
			// 排序 Sorting
			@RequestParam(defaultValue = "created_date") String orderBy,
			@RequestParam(defaultValue = "desc") String sort,
			
			// 分頁 Pagination
			@RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
			@RequestParam(defaultValue = "0") @Min(0) Integer offset){
		ResponseVO response = new ResponseVO();
		
		try {
			// 整理Request參數
			OrderQueryParams params = new OrderQueryParams();
			params.setUserId(userId);
			params.setOrderBy(orderBy);
			params.setSort(sort);
			params.setLimit(limit);
			params.setOffset(offset);

			List<OrderVO> list = orderService.getOrders(params);

			Integer total = orderService.countOrder(params);
			
			// Response查詢結果與分頁資訊
			Page<OrderVO> pageObj = new Page<>();
			pageObj.setLimit(limit);
			pageObj.setOffset(offset);
			pageObj.setTotal(total);
			pageObj.setResult(list);

			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("result", pageObj);			
		} catch (Exception e) {
			logger.error("OrderController [getOrders] Error: {}", ExceptionUtils.getStackTrace(e));
			response.setRtnCode(CodeType.FAIL.getCode());
			response.setRtnMsg(CodeType.FAIL.getMessage());
		}		
		
		return ResponseEntity.status(HttpStatus.OK).body(response);		
	}

	@PostMapping()
	public ResponseEntity<ResponseVO> createOrder(
			@PathVariable Integer userId,
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
