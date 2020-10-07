package com.systango.shopping.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListDTO {
	
    @NotNull(message="category is required")
    private Integer categoryId;
    
    @Valid
    @NotNull(message="products required")
    private List<ProductDTO> products;
    
}
