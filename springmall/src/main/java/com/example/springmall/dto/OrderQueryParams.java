package com.example.springmall.dto;

import lombok.Data;

@Data
public class OrderQueryParams {
	private Integer userId;
	private String orderBy;
	private String sort;
	private Integer limit;
	private Integer offset;
}
