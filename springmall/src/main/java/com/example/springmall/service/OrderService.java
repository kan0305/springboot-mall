package com.example.springmall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springmall.dao.OrderDao;
import com.example.springmall.dao.ProductDao;
import com.example.springmall.dto.OrderCreateRequest;
import com.example.springmall.dto.OrderItemRequest;
import com.example.springmall.model.OrderItemVO;
import com.example.springmall.model.OrderVO;
import com.example.springmall.model.ProductVO;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Transactional
	public Integer createOrder(Integer userId, OrderCreateRequest request) {

		int totalAmount = 0;

		List<OrderItemVO> list = new ArrayList<>();

		for (OrderItemRequest item : request.getOrderItemList()) {
			ProductVO product = productDao.getProductById(item.getProductId());

			// 計算訂單總價
			if (product != null) {
				int amount = product.getPrice() * item.getQuantity();
				totalAmount += amount;

				// 建立orderItem
				OrderItemVO orderItem = new OrderItemVO();
				orderItem.setProductId(item.getProductId());
				orderItem.setQuantity(item.getQuantity());
				orderItem.setAmount(amount);

				// 存放orderItem
				list.add(orderItem);
			}

		}

		// 創建訂單
		Integer orderId = orderDao.createOrder(userId, totalAmount);

		if (orderId != -1) {
			// 建立訂單內容
			orderDao.createOrderItems(orderId, list);
		}

		return orderId;
	}

	public OrderVO getOrderById(Integer orderId) {
		OrderVO order = orderDao.getOrderById(orderId);

		List<OrderItemVO> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());

		order.setOrderItemList(orderItemList);

		return order;
	}

}
