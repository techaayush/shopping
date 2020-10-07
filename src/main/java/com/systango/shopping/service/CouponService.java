package com.systango.shopping.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.systango.shopping.apiresponse.ApiResponse;
import com.systango.shopping.dto.CouponDTO;
import com.systango.shopping.entity.Coupon;
import com.systango.shopping.repository.CouponRepository;

@Service
public class CouponService {
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Transactional
	public ResponseEntity<ApiResponse> addCoupon(@Valid CouponDTO couponDTO) {
		Coupon coupon = couponRepository.findByCouponCode(couponDTO.getCode());
		
		if(coupon != null) {
    		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("Coupon code already exist",null));
		}
		
		coupon = new Coupon();
		
		coupon.setCouponCode(couponDTO.getCode());
		coupon.setMinimumValue(couponDTO.getMinValue());
		coupon.setValue(couponDTO.getValue());
		
		couponRepository.save(coupon);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	public ResponseEntity<ApiResponse> getCoupons() {
		return ResponseEntity.ok().body(new ApiResponse("Coupons list",couponRepository.findAll()));
	}

}
