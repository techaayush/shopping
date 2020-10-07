package com.systango.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systango.shopping.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer>{

	Coupon findByCouponCode(String code);

}
