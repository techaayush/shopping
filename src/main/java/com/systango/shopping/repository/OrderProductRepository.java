package com.systango.shopping.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.systango.shopping.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Integer>{

}
