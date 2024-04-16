package com.example.springmall.model;

import java.util.Date;

import com.example.springmall.constant.ProductCategory;

import lombok.Data;

@Data
public class ProductVO {
	private Integer productId;
	private String productName;
	private ProductCategory category;
	private String imageUrl;
	private Integer price;
	private Integer stock;
	private String description;
	private Date createdDate;
	private Date lastModifiedDate;
	
}
