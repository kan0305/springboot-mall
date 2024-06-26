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
import com.example.springmall.dto.ProductQueryParams;
import com.example.springmall.dto.ProductRequest;
import com.example.springmall.model.ProductVO;
import com.example.springmall.rowmapper.ProductRowMapper;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Integer countProduct(ProductQueryParams params) {
		String sql = "SELECT count(*) FROM product WHERE 1=1";

		Map<String, Object> map = new HashMap<>();
		
		// 拼接查詢條件
		sql = addFilteringSql(sql, map, params);

		return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
	}
	
	@Override
	public List<ProductVO> getProducts(ProductQueryParams params) {
		String sql = "SELECT product_id, product_name, category, " 
				+ "image_url, price, stock, description, "
				+ "created_date, last_modified_date" 
				+ " FROM product WHERE 1=1";

		Map<String, Object> map = new HashMap<>();

		// 拼接查詢條件
		sql = addFilteringSql(sql, map, params);

		// 排序
		sql = sql + " ORDER BY " + params.getOrderBy() + " " + params.getSort();

		// 分頁
		sql = sql + " LIMIT :limit OFFSET :offset";
		map.put("limit", params.getLimit());
		map.put("offset", params.getOffset());

		return namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
	}

	@Override
	public ProductVO getProductById(Integer productId) {
		String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, "
				+ "created_date, last_modified_date" 
				+ " FROM product " 
				+ "WHERE product_id = :productId";

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
		String sql = "UPDATE product " + "SET product_name=:productName, category=:category, "
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

	@Override
	public void deleteProduct(Integer productId) {
		String sql = "DELETE FROM product WHERE product_id=:productId";

		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);

		namedParameterJdbcTemplate.update(sql, map);
	}	
	
	private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams params) {
		// 查詢條件(category)
		if (params.getCategory() != null) {
			sql = sql + " AND category=:category";
			map.put("category", params.getCategory().toString());
		}

		// 查詢條件(search)
		if (params.getSearch() != null) {
			sql = sql + " AND product_name LIKE :search";
			map.put("search", "%" + params.getSearch() + "%");
		}
		
		return sql;
	}

	@Override
	public void updateStock(Integer productId, Integer stock) {
		String sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate "
				+ "WHERE product_id = :productId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		map.put("stock", stock);
		map.put("lastModifiedDate", new Date());

		namedParameterJdbcTemplate.update(sql, map);
	}
}
