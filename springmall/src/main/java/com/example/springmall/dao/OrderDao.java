package com.example.springmall.dao;

import java.util.List;

import com.example.springmall.model.OrderItemVO;

public interface OrderDao {
	Integer createOrder(Integer userId, Integer totalAmount);
	void createOrderItems(Integer orderId, List<OrderItemVO> list);
}
