package com.example.springmall.util;

import java.util.List;

import lombok.Data;

@Data
public class Page<T> {
	private Integer limit;
	private Integer offset;
	private Integer total;
	private List<T> result;
}
