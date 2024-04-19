package com.example.springmall.model;

import java.util.Date;

import lombok.Data;

@Data
public class OrderVO {
	private Integer orderId;
	private Integer userId;
	private Integer totalAmount;
	private Date createdDate;
	private Date lastModifiedDate;
}
