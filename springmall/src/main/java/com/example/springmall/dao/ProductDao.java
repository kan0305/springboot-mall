package com.example.springmall.dao;

import java.util.List;

import com.example.springmall.dto.ProductQueryParams;
import com.example.springmall.dto.ProductRequest;
import com.example.springmall.model.ProductVO;

public interface ProductDao {
	List<ProductVO> getProducts(ProductQueryParams params);
	ProductVO getProductById(Integer productId);
	Integer createProduct(ProductRequest product);
	void updateProduct(Integer productId, ProductRequest product);
	void deleteProduct(Integer productId);
	Integer countProduct(ProductQueryParams params);
}
