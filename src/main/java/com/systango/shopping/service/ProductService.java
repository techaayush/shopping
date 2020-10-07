package com.systango.shopping.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systango.shopping.apiresponse.ApiResponse;
import com.systango.shopping.dto.ProductDTO;
import com.systango.shopping.dto.ProductListDTO;
import com.systango.shopping.entity.Category;
import com.systango.shopping.entity.Product;
import com.systango.shopping.repository.CategoryRepository;
import com.systango.shopping.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional
	public ResponseEntity<Object> addProducts(ProductListDTO productsDTO) {
		Optional<Category> optionalCategory = categoryRepository.findById(productsDTO.getCategoryId());
		
		if(!optionalCategory.isPresent())
    		return ResponseEntity.notFound().build();
			
		Category category = optionalCategory.get();
		
		List<ProductDTO> productDTOList = productsDTO.getProducts();
		
		for(ProductDTO productDTO: productDTOList) {
			Product product = new Product();
			
			product.setCategory(category);
			product.setName(productDTO.getName());
			product.setDescription(productDTO.getDescription());
			product.setPrice(productDTO.getPrice());
			
			productRepository.save(product);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}


	public ResponseEntity<ApiResponse> getProductsByCategory(Integer categoryId) {
		List<Product> categoryProducts = productRepository.findByCategoryId(categoryId);
		
		if(categoryProducts.size() < 1) {
    		return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new ApiResponse("Products list",categoryProducts));
	}


	public ResponseEntity<ApiResponse> getProduct(Integer productId) {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if(!optionalProduct.isPresent())
    		return ResponseEntity.notFound().build();
		
		Product product = optionalProduct.get();
		
		Map<String,Object> productData = new HashMap<String,Object>();
		
		productData.put("id", product.getId());
		productData.put("name", product.getName());
		productData.put("description", product.getDescription());
		productData.put("price", product.getPrice());
		
		return ResponseEntity.ok(new ApiResponse("Product found",productData));
	}

	@Transactional
	public ResponseEntity<Object> updateProduct(Integer productId, ProductDTO productUpdateRequest) {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if(!optionalProduct.isPresent())
    		return ResponseEntity.notFound().build();
		
		Product product = optionalProduct.get();
		
		product.setName(productUpdateRequest.getName());
		product.setDescription(productUpdateRequest.getDescription());
		product.setPrice(productUpdateRequest.getPrice());

		productRepository.save(product);
		
		return ResponseEntity.noContent().build();
	}

	@Transactional
	public ResponseEntity<Object> deleteProduct(Integer productId) {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if(!optionalProduct.isPresent())
    		return ResponseEntity.noContent().build();
		
		productRepository.deleteById(productId);
		return ResponseEntity.noContent().build();
	}
	
}
