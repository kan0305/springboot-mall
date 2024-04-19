package com.example.springmall.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	Logger logger = LoggerFactory.getLogger(OrderService.class);

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

			if (product == null) {
				logger.warn("商品ID:{}不存在", item.getProductId());
				return -1;
			}

			if (product.getStock() < item.getQuantity()) {
				logger.warn("商品{}庫存數量不足，無法購買。剩餘庫存{}，欲購買數量{}。", product.getProductId(), product.getStock(),
						item.getQuantity());
				return -1;
			}

			// 計算訂單總價
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
