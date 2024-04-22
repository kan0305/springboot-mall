package com.example.springmall.dao;

import java.util.List;

import com.example.springmall.model.OrderItemVO;
import com.example.springmall.model.OrderVO;

public interface OrderDao {
	OrderVO getOrderById(Integer orderId);
	
	List<OrderItemVO> getOrderItemsByOrderId(Integer orderId);
	
	Integer createOrder(Integer userId, Integer totalAmount);
	
	void createOrderItems(Integer orderId, List<OrderItemVO> list);
}
