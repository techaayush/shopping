package com.systango.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systango.shopping.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	public interface CategoryProduct {
		Integer getId();
		String getName();
		String getDescription();
		Double getPrice();
	}
	
//    @Query("SELECT p.id as id, p.name as name, p.description as description, p.price as price from Product p WHERE p.category.id = :categoryId")
	List<Product> findByCategoryId(Integer categoryId);
	
}
