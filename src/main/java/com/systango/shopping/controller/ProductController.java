package com.systango.shopping.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.systango.shopping.apiresponse.ApiResponse;
import com.systango.shopping.dto.ProductDTO;
import com.systango.shopping.dto.ProductListDTO;
import com.systango.shopping.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
    @Autowired
    private ProductService productService;
    
    @Secured("ROLE_ADMIN")
    @PostMapping("/products")
    public ResponseEntity<Object> addProducts(@Valid @RequestBody ProductListDTO products) {
    	List<ProductDTO> productsDTOList = products.getProducts();
    	
		if(productsDTOList.isEmpty()) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Atleast 1 product is required");
		}
		
    	return productService.addProducts(products);
    }
    
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/categories/{id}/products")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable("id") Integer categoryId) {
    	return productService.getProductsByCategory(categoryId);
    }
    
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse> getProduct(@PathVariable("id") Integer productId) {
    	return productService.getProduct(productId);
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Integer productId, @Valid @RequestBody ProductDTO productUpdateRequest) {
    	return productService.updateProduct(productId, productUpdateRequest);
    }
    
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Integer productId) {
    	return productService.deleteProduct(productId);
    }
    
}
