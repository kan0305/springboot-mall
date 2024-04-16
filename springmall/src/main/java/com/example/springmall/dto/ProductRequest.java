package com.example.springmall.dto;

import com.example.springmall.constant.ProductCategory;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Objects
 */
@Data
public class ProductRequest {
	@NotNull
	private String productName;
	
	@NotNull
	private ProductCategory category;
	
	@NotNull
	private String imageUrl;
	
	@NotNull
	private Integer price;
	
	@NotNull
	private Integer stock;
	
	private String description;
}
