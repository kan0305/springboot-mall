package com.example.springmall.model;

import lombok.Data;

@Data
public class OrderItemVO {
	private Integer orderItemId;
	private Integer orderId;
	private Integer productId;
	private Integer quantity;
	private Integer amount;
}
