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

import com.example.springmall.dao.OrderDao;
import com.example.springmall.model.OrderItemVO;

@Repository
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Integer createOrder(Integer userId, Integer totalAmount) {
		String sql = "INSERT INTO `order`(user_Id, total_amount, created_date, last_modified_date) "
				+ "VALUES(:userId, :totalAmount, :createdDate, :lastModifiedDate)";

		Map<String, Object> map = new HashMap<>();

		map.put("userId", userId);
		map.put("totalAmount", totalAmount);

		Date now = new Date();
		map.put("createdDate", now);
		map.put("lastModifiedDate", now);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

		return Optional.ofNullable(keyHolder.getKey()).map(Number::intValue).orElse(-1);
	}

	@Override
	public void createOrderItems(Integer orderId, List<OrderItemVO> list) {
		String sql = "INSERT INTO `order_item`(order_id, product_id, quantity, amount) "
				+ "VALUES(:orderId, :productId, :quantity, :amount)";
		
		MapSqlParameterSource[] parameterSources =  new MapSqlParameterSource[list.size()];
		
		for(int i = 0; i < list.size(); i++) {
			parameterSources[i] = new MapSqlParameterSource();
			parameterSources[i].addValue("orderId", orderId);
			parameterSources[i].addValue("productId", list.get(i).getProductId());
			parameterSources[i].addValue("quantity", list.get(i).getQuantity());
			parameterSources[i].addValue("amount", list.get(i).getAmount());
		}
		
		namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
		
	}

}
