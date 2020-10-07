package com.systango.shopping.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.systango.shopping.apiresponse.ApiResponse;
import com.systango.shopping.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
    @Autowired
    private CategoryService categoryService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/categories")
    public ResponseEntity<Object> addCategory(@RequestBody Map<String,String> payload) {
    	if(payload.get("name") == null || payload.get("name").isEmpty()) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "category name is required");
    	}
    	
    	return categoryService.addCategory(payload.get("name"));
    }
    
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse> getCategories() {
    	return categoryService.getCategories();
    }
    
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse> getCategory(@PathVariable("id") Integer categoryId) {
    	return categoryService.getCategory(categoryId);
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/categories/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable("id") Integer categoryId, @RequestBody Map<String,String> payload) {
    	if(payload.get("name") == null || payload.get("name").isEmpty())
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "category name is required");
    	
    	return categoryService.updateCategory(categoryId, payload.get("name"));
    }
}
