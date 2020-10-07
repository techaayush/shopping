package com.systango.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductDetailDTO {
	
	private Integer id;
	
	private String name;
	
	private String description;
	
	private Double price;
	
	private Integer quantity;
	
	private Double totalPrice;
}
