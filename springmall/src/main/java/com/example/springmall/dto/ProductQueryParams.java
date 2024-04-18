package com.example.springmall.dto;

import com.example.springmall.constant.ProductCategory;

import lombok.Data;

@Data
public class ProductQueryParams {
	private ProductCategory category;
	private String search;
}
