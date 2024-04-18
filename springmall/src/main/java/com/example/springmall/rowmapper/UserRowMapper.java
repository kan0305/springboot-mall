package com.example.springmall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.springmall.model.UserVO;

public class UserRowMapper implements RowMapper<UserVO> {

	@Override
	public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserVO user = new UserVO();
		
		user.setUserId(rs.getInt("user_id"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setCreatedDate(rs.getTimestamp("created_date"));
		user.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
		
		return user;
	}

}
