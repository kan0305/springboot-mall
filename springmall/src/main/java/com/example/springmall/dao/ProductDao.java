package com.example.springmall.dao;

import com.example.springmall.dto.ProductRequest;
import com.example.springmall.model.ProductVO;

public interface ProductDao {
	ProductVO getProductById(Integer productId);
	Integer createProduct(ProductRequest product);
	void updateProduct(Integer productId, ProductRequest product);
}
