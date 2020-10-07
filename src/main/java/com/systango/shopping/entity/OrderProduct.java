package com.systango.shopping.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_products")
public class OrderProduct extends AuditModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2096576042439111025L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer quantity;
	
	private Double price;
 
	@ManyToOne
	@JoinColumn(name = "product_id",nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "order_id",nullable = false)
	private Order order;
	
	public Double getTotalPrice() {
        return getPrice() * getQuantity();
    }
	
}
