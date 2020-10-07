package com.systango.shopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coupons")
public class Coupon {
	
	@Id
	@Column(name = "code",length = 20)
	private String couponCode; 
	
	private Double value;
	
	@Column(name = "min_value")
	private Double minimumValue;
}
