package com.example.springmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.springmall.dao.ProductDao;
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

}
