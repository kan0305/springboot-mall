package com.example.springmall.model;

import java.util.Date;

import lombok.Data;

@Data
public class ProductVO {
	private Integer productId;
	private String productName;
	private String category;
	private String imageUrl;
	private Integer price;
	private Integer stock;
	private String description;
	private Date createdDate;
	private Date lastModifiedDate;
	
}
