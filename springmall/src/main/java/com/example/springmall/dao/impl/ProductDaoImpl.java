package com.example.springmall.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.springmall.dao.ProductDao;
import com.example.springmall.dto.ProductRequest;
import com.example.springmall.model.ProductVO;
import com.example.springmall.rowmapper.ProductRowMapper;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public ProductVO getProductById(Integer productId) {
		String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, "
				+ "created_date, last_modified_date" + " FROM product " + "WHERE product_id = :productId";

		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);

		List<ProductVO> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

		if (!productList.isEmpty()) {
			return productList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Integer createProduct(ProductRequest product) {
		String sql = "INSERT INTO product (product_name, category, image_url, price, stock, "
				+ "description, created_date, last_modified_date) "
				+ "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, "
				+ ":createdDate, :lastModifiedDate)";
		
		Map<String, Object> map = new HashMap<>();
		map.put("productName", product.getProductName());
		map.put("category", product.getCategory().toString());
		map.put("imageUrl", product.getImageUrl());
		map.put("price", product.getPrice());
		map.put("stock", product.getStock());
		map.put("description", product.getDescription());
		
		Date now = new Date();
		map.put("createdDate", now);
		map.put("lastModifiedDate", now);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		
		return Optional.ofNullable(keyHolder.getKey()).map(Number::intValue).orElse(-1);
	}

	@Override
	public void updateProduct(Integer productId, ProductRequest product) {
		String sql = "UPDATE product "
				+ "SET product_name=:productName, category=:category, "
				+ "image_url=:imageUrl, price=:price, stock=:stock, "
				+ "description=:description, last_modified_date=:lastModifiedDate "
				+ "WHERE product_id=:productId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		
		map.put("productName", product.getProductName());
		map.put("category", product.getCategory().toString());
		map.put("imageUrl", product.getImageUrl());
		map.put("price", product.getPrice());
		map.put("stock", product.getStock());
		map.put("description", product.getDescription());
		map.put("lastModifiedDate", new Date());		
		
		namedParameterJdbcTemplate.update(sql, map);
	}

}
