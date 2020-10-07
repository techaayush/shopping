package com.systango.shopping.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systango.shopping.apiresponse.ApiResponse;
import com.systango.shopping.dto.CouponDTO;
import com.systango.shopping.service.CouponService;

@RestController
@RequestMapping("/api")
public class CouponController {
	
    @Autowired
    private CouponService couponService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/coupons")
    public ResponseEntity<ApiResponse> addCoupon(@Valid @RequestBody CouponDTO couponDTO) {
    	return couponService.addCoupon(couponDTO);
    }
    
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/coupons")
    public ResponseEntity<ApiResponse> getCoupons() {
    	return couponService.getCoupons();
    }
    
}
