package com.example.springmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springmall.dao.ProductDao;
import com.example.springmall.dto.ProductRequest;
import com.example.springmall.model.ProductVO;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public ProductVO getProductById(Integer productId) {
		return productDao.getProductById(productId);
	}
	
	public Integer createProduct(ProductRequest product) {
		return productDao.createProduct(product);
	}
}
