package com.example.springmall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.springmall.model.OrderVO;

public class OrderRowMapper implements RowMapper<OrderVO> {

	@Override
	public OrderVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderVO order = new OrderVO();
		
		order.setOrderId(rs.getInt("order_id"));
		order.setUserId(rs.getInt("user_id"));
		order.setTotalAmount(rs.getInt("total_amount"));
		order.setCreatedDate(rs.getTimestamp("created_date"));
		order.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
		
		return order;
	}
	
}
