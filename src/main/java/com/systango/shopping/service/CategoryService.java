package com.systango.shopping.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systango.shopping.apiresponse.ApiResponse;
import com.systango.shopping.entity.Category;
import com.systango.shopping.repository.CategoryRepository;


@Service
public class CategoryService {
	
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Transactional
	public ResponseEntity<Object> addCategory(String categoryName) {
		Category category = new Category();
		
		category.setName(categoryName);
		categoryRepository.save(category);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	public ResponseEntity<ApiResponse> getCategories() {		
		return ResponseEntity.ok(new ApiResponse("Categories list",categoryRepository.findAll()));
	}

	public ResponseEntity<ApiResponse> getCategory(Integer categoryId) {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		if(!optionalCategory.isPresent()) {
    		return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new ApiResponse("Category found",optionalCategory.get()));
	}
	
	@Transactional
	public ResponseEntity<Object> updateCategory(Integer categoryId, String categoryName) {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		if(!optionalCategory.isPresent()) {
    		return ResponseEntity.notFound().build();
		}
		
		Category category = optionalCategory.get();
		category.setName(categoryName);
		categoryRepository.save(category);
		
		return ResponseEntity.noContent().build();
	}

	
}
