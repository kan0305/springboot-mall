package com.example.springmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springmall.dao.ProductDao;
import com.example.springmall.dto.ProductQueryParams;
import com.example.springmall.dto.ProductRequest;
import com.example.springmall.model.ProductVO;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public List<ProductVO> getProducts(ProductQueryParams params){
		return productDao.getProducts(params);
	}
	
	public ProductVO getProductById(Integer productId) {
		return productDao.getProductById(productId);
	}
	
	public Integer createProduct(ProductRequest product) {
		return productDao.createProduct(product);
	}
	
	public void updateProduct(Integer productId, ProductRequest product) {
		productDao.updateProduct(productId, product);
	}
	
	public void deleteProduct(Integer productId) {
		productDao.deleteProduct(productId);
	}
	
	public Integer countProduct(ProductQueryParams params) {
		return productDao.countProduct(params);
	}
}
