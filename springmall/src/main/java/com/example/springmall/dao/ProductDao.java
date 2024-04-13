package com.example.springmall.dao;

import com.example.springmall.model.ProductVO;

public interface ProductDao {
	ProductVO getProductById(Integer productId);
}
