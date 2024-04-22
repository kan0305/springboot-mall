package com.example.springmall.dao;

import java.util.List;

import com.example.springmall.dto.OrderQueryParams;
import com.example.springmall.model.OrderItemVO;
import com.example.springmall.model.OrderVO;

public interface OrderDao {
	Integer countOrder(OrderQueryParams params);
	
	OrderVO getOrderById(Integer orderId);
	
	List<OrderVO> getOrders(OrderQueryParams params);
	
	List<OrderItemVO> getOrderItemsByOrderId(Integer orderId);
	
	Integer createOrder(Integer userId, Integer totalAmount);
	
	void createOrderItems(Integer orderId, List<OrderItemVO> list);
}
