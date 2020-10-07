package com.systango.shopping.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponDTO {

	@NotBlank(message="code is required")
	private String code;
	
	@NotNull(message = "minimum order value is required")
	private Double minValue;

	@NotNull(message = "coupon value is required")
	private Double value;
	
}
