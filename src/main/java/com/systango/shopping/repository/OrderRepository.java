package com.systango.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.systango.shopping.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	
	public interface UserOrder {
		Integer getId();
		Double getDiscount();
		Double getTotalPrice();
	}
	
	@Query("SELECT o.id AS id, o.totalAmount AS totalPrice, o.discountAmount AS discount FROM Order o WHERE o.user.id = :userId")
	List<UserOrder> findByUserId(@Param("userId") Integer userId);
}
