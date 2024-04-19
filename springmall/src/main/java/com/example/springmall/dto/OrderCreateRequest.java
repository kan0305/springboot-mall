package com.example.springmall.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderCreateRequest {
	
	@NotEmpty
	private List<OrderItemRequest> orderItemList;
}
