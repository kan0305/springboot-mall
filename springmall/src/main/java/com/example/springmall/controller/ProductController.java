package com.example.springmall.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springmall.common.ResponseVO;
import com.example.springmall.common.ResponseVO.CodeType;
import com.example.springmall.dto.ProductRequest;
import com.example.springmall.model.ProductVO;
import com.example.springmall.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@GetMapping("/{productId}")
	public ResponseEntity<ResponseVO> getProductById(@PathVariable Integer productId) {
		ResponseVO response = new ResponseVO();
		ProductVO product = null;

		try {
			product = productService.getProductById(productId);
		} catch (Exception e) {
			logger.error("ProductController [getProductById] Error: {}", ExceptionUtils.getStackTrace(e));
		}

		if (product != null) {
			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("product", product);
		} else {
			logger.info("查無商品, 查詢productId = {}", productId);
			response.setRtnCode(CodeType.FAIL.getCode());
			response.setRtnMsg(CodeType.FAIL.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping()
	public ResponseEntity<ResponseVO> createProduct(@RequestBody @Valid ProductRequest productRequest) {
		ResponseVO response = new ResponseVO();
		ProductVO newProduct = null;
		try {
			Integer productId = productService.createProduct(productRequest);
			if (productId != -1) {
				logger.info("商品[{}]建立成功", productRequest.getProductName());
				newProduct = productService.getProductById(productId);
			} else {
				logger.info("商品[{}]建立失敗", productRequest.getProductName());
			}
		} catch (Exception e) {
			logger.error("ProductController [createProduct] Error: {}", ExceptionUtils.getStackTrace(e));
		}	

		if (newProduct != null) {
			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("product", newProduct);
		} else {
			response.setRtnCode(CodeType.FAIL.getCode());
			response.setRtnMsg(CodeType.FAIL.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ResponseVO> updateProduct(
			@PathVariable Integer productId, 
			@RequestBody @Valid ProductRequest productRequest){
		ResponseVO response = new ResponseVO();
		ProductVO newProduct = null;
		try {
			ProductVO product = productService.getProductById(productId);
			
			if(product == null) {
				logger.info("查無商品, 查詢productId= {}", productId);
				response.setRtnCode(CodeType.PRODUCT_NOT_FOUND.getCode());
				response.setRtnMsg(CodeType.PRODUCT_NOT_FOUND.getMessage());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
			
			productService.updateProduct(productId, productRequest);
			
			newProduct = productService.getProductById(productId);
		} catch (Exception e) {
			logger.error("ProductController [updateProduct] Error: {}", ExceptionUtils.getStackTrace(e));
		}	

		if (newProduct != null) {
			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("product", newProduct);
		} else {
			response.setRtnCode(CodeType.FAIL.getCode());
			response.setRtnMsg(CodeType.FAIL.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
		
	}
}
