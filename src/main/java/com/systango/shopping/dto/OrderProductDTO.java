package com.systango.shopping.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductDTO {
	
	@NotNull(message = "product id is required")
	private Integer id;
	
	@NotNull(message = "quantity of product is required")
	private Integer quantity;
	
}
