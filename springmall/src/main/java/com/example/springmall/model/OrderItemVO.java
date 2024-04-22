package com.example.springmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class OrderItemVO {
	private Integer orderItemId;
	private Integer orderId;
	private Integer productId;
	private Integer quantity;
	private Integer amount;
	
	private String productName;
	private String imageUrl;
	
	@JsonIgnore
	private Integer stock;
}
