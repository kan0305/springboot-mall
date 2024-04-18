package com.example.springmall.controller;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springmall.common.ResponseVO;
import com.example.springmall.common.ResponseVO.CodeType;
import com.example.springmall.constant.ProductCategory;
import com.example.springmall.dto.ProductQueryParams;
import com.example.springmall.dto.ProductRequest;
import com.example.springmall.model.ProductVO;
import com.example.springmall.service.ProductService;
import com.example.springmall.util.Page;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/products")
public class ProductController {
	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@GetMapping()
	public ResponseEntity<ResponseVO> getProducts(
			// 查詢條件 Filtering
			@RequestParam(required = false) ProductCategory category,
			@RequestParam(required = false) String search,
			
			// 排序 Sorting
			@RequestParam(defaultValue = "created_date") String orderBy,
			@RequestParam(defaultValue = "desc") String sort,
			
			// 分頁 Pagination
			@RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
			@RequestParam(defaultValue = "0") @Min(0) Integer offset) {
		ResponseVO response = new ResponseVO();
		
		try {
			// 整理Request參數
			ProductQueryParams params = new ProductQueryParams();
			params.setCategory(category);
			params.setSearch(search);
			params.setOrderBy(orderBy);
			params.setSort(sort);
			params.setLimit(limit);
			params.setOffset(offset);		
			
			// 取得 product list
			List<ProductVO> list = productService.getProducts(params);
			
			// 取得 product 總數
			Integer total = productService.countProduct(params);
			
			// Response查詢結果與分頁資訊
			Page<ProductVO> pageObj = new Page<>();
			pageObj.setLimit(limit);
			pageObj.setOffset(offset);
			pageObj.setTotal(total);
			pageObj.setResult(list);
			
			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("result", pageObj);
			
		} catch (Exception e) {
			logger.error("ProductController [getProducts] Error: {}", ExceptionUtils.getStackTrace(e));
			response.setRtnCode(CodeType.FAIL.getCode());
			response.setRtnMsg(CodeType.FAIL.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ResponseVO> getProductById(@PathVariable Integer productId) {
		ResponseVO response = new ResponseVO();

		try {
			ProductVO product = productService.getProductById(productId);

			if (product == null) {
				logger.info("查無商品, 查詢productId = {}", productId);
				response.setRtnCode(CodeType.PRODUCT_NOT_FOUND.getCode());
				response.setRtnMsg(CodeType.PRODUCT_NOT_FOUND.getMessage());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("product", product);

		} catch (Exception e) {
			logger.error("ProductController [getProductById] Error: {}", ExceptionUtils.getStackTrace(e));
			response.setRtnCode(CodeType.FAIL.getCode());
			response.setRtnMsg(CodeType.FAIL.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping()
	public ResponseEntity<ResponseVO> createProduct(@RequestBody @Valid ProductRequest productRequest) {
		ResponseVO response = new ResponseVO();

		try {
			Integer productId = productService.createProduct(productRequest);

			if (productId == -1) {
				logger.info("商品[{}]建立失敗", productRequest.getProductName());
				response.setRtnCode(CodeType.PRODUCT_CREATE_FAIL.getCode());
				response.setRtnMsg(CodeType.PRODUCT_CREATE_FAIL.getMessage());
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}

			logger.info("商品[{}]建立成功", productRequest.getProductName());
			ProductVO newProduct = productService.getProductById(productId);

			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("product", newProduct);

		} catch (Exception e) {
			logger.error("ProductController [createProduct] Error: {}", ExceptionUtils.getStackTrace(e));
			response.setRtnCode(CodeType.FAIL.getCode());
			response.setRtnMsg(CodeType.FAIL.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<ResponseVO> updateProduct(@PathVariable Integer productId,
			@RequestBody @Valid ProductRequest productRequest) {
		ResponseVO response = new ResponseVO();

		try {
			ProductVO product = productService.getProductById(productId);

			if (product == null) {
				logger.info("查無商品, 查詢productId= {}", productId);
				response.setRtnCode(CodeType.PRODUCT_NOT_FOUND.getCode());
				response.setRtnMsg(CodeType.PRODUCT_NOT_FOUND.getMessage());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			productService.updateProduct(productId, productRequest);

			ProductVO newProduct = productService.getProductById(productId);

			response.setRtnCode(CodeType.SUCCESS.getCode());
			response.setRtnMsg(CodeType.SUCCESS.getMessage());
			response.getRtnObj().put("product", newProduct);

		} catch (Exception e) {
			logger.error("ProductController [updateProduct] Error: {}", ExceptionUtils.getStackTrace(e));
			response.setRtnCode(CodeType.FAIL.getCode());
			response.setRtnMsg(CodeType.FAIL.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
		try {
			productService.deleteProduct(productId);
		} catch (Exception e) {
			logger.error("ProductController [deleteProduct] Error: {}", ExceptionUtils.getStackTrace(e));
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
