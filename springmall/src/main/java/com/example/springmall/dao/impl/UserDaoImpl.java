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

import com.example.springmall.dao.UserDao;
import com.example.springmall.dto.UserRegisterRequest;
import com.example.springmall.model.UserVO;
import com.example.springmall.rowmapper.UserRowMapper;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Integer createUser(UserRegisterRequest request) {
		String sql = "INSERT INTO user (email, password, created_date, last_modified_date) "
				+ "VALUES (:email, :password, :createdDate, :lastModifiedDate)";

		Map<String, Object> map = new HashMap<>();

		map.put("email", request.getEmail());
		map.put("password", request.getPassword());

		Date now = new Date();
		map.put("createdDate", now);
		map.put("lastModifiedDate", now);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

		return Optional.ofNullable(keyHolder.getKey()).map(Number::intValue).orElse(-1);
	}

	@Override
	public UserVO getUserById(Integer userId) {
		String sql = "SELECT user_id, email, password, created_date, last_modified_date "
				+ "FROM user WHERE user_id = :userId";

		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);

		List<UserVO> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

		if (!userList.isEmpty()) {
			return userList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public UserVO getUserByEmail(String email) {
		String sql = "SELECT user_id, email, password, created_date, last_modified_date "
				+ "FROM user WHERE email = :email";

		Map<String, Object> map = new HashMap<>();
		map.put("email", email);

		List<UserVO> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

		if (!userList.isEmpty()) {
			return userList.get(0);
		} else {
			return null;
		}
	}
}
