package com.systango.shopping.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
	
	private String coupon;
	
	@Valid
	@NotNull(message = "products required")
	private List<OrderProductDTO> products;
	
}
