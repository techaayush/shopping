package com.systango.shopping.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
	
    @NotBlank(message="product name is required")
    private String name;
    
    @NotBlank(message="product description is required")
    private String description;
    
    @NotNull(message="product price is required")
    private Double price;
    
}
