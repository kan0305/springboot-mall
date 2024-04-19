package com.example.springmall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.springmall.model.OrderItemVO;

public class OrderItemRowMapper implements RowMapper<OrderItemVO> {

	@Override
	public OrderItemVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderItemVO orderItem = new OrderItemVO();
		
		orderItem.setOrderItemId(rs.getInt("order_item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setProductId(rs.getInt("product_id"));
		orderItem.setQuantity(rs.getInt("quantity"));
		orderItem.setAmount(rs.getInt("amount"));
		
		orderItem.setProductName(rs.getString("product_name"));
		orderItem.setImageUrl(rs.getString("image_url"));
		
		return orderItem;
	}

}
