package com.systango.shopping.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends AuditModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6383112305815067371L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;
	
	@Column(name = "discount_amount")
	private Double discountAmount;
	
	@Column(name = "total_amount",nullable = false)
	private Double totalAmount;
	
	public Double getTotalOrderPrice() {
        Double sum = 0.0;
        List<OrderProduct> orderProducts = getOrderProducts();
        for (OrderProduct orderProduct : orderProducts) {
            sum += orderProduct.getTotalPrice();
        }
        return sum;
    }
	
	public Double getAmountPaid() {
        Double amountPaid = discountAmount != null ? (totalAmount - discountAmount):totalAmount;
		return amountPaid;
	}
 
    public int getNumberOfProducts() {
        return orderProducts.size();
    }
	
}
