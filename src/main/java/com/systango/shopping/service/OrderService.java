package com.systango.shopping.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.systango.shopping.apiresponse.ApiResponse;
import com.systango.shopping.dto.OrderDTO;
import com.systango.shopping.dto.OrderDetailsDTO;
import com.systango.shopping.dto.OrderProductDTO;
import com.systango.shopping.dto.OrderProductDetailDTO;
import com.systango.shopping.entity.Coupon;
import com.systango.shopping.entity.Order;
import com.systango.shopping.entity.OrderProduct;
import com.systango.shopping.entity.Product;
import com.systango.shopping.entity.User;
import com.systango.shopping.entity.UserWallet;
import com.systango.shopping.repository.CouponRepository;
import com.systango.shopping.repository.OrderProductRepository;
import com.systango.shopping.repository.OrderRepository;
import com.systango.shopping.repository.OrderRepository.UserOrder;
import com.systango.shopping.repository.ProductRepository;
import com.systango.shopping.repository.UserRepository;
import com.systango.shopping.repository.UserWalletRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private UserWalletRepository userWalletRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;
	
	@Transactional
	public ResponseEntity<ApiResponse> createOrder(CustomUserDetails currentUser, OrderDTO orderDTO) {
		
		Coupon coupon = null;
	    Double totalOrderPrice = 0.00;

		User user = userRepository.findById(currentUser.getId()).get();
		
		List<OrderProductDTO> orderProductDTOList = orderDTO.getProducts();
	    List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
	    
	    for(OrderProductDTO orderProductDTO:orderProductDTOList) {
		   Optional<Product> optionalProduct = productRepository.findById(orderProductDTO.getId());
		   
		   if(!optionalProduct.isPresent())
		      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product with id "+orderProductDTO.getId()+" not found",null));
		   
		   Product product = optionalProduct.get();
		   
		   totalOrderPrice += (product.getPrice() * orderProductDTO.getQuantity());
		   
		   OrderProduct orderProduct = new OrderProduct();
		   
		   orderProduct.setQuantity(orderProductDTO.getQuantity());
		   orderProduct.setPrice(product.getPrice());
		   orderProduct.setProduct(product);
		   
		   orderProductList.add(orderProduct);
	    }
	    
	    Double couponValue = null;
		if(orderDTO.getCoupon() != null) {
		   coupon = couponRepository.findByCouponCode(orderDTO.getCoupon());
		   if(coupon == null) {
	    	  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Coupon is not valid",null));
		   }
		   Double minimumOrderValue = coupon.getMinimumValue();		   
		   if(totalOrderPrice < minimumOrderValue)
		      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Coupon is valid for minimum of Rs."+minimumOrderValue,null));

		   couponValue = coupon.getValue();
		}
		
		UserWallet userWallet = userWalletRepository.findByUserId(user.getId());
	    
	    Double userWalletBalance = userWallet.getBalance();
	    Double amountToPay = couponValue != null ? (totalOrderPrice - couponValue): totalOrderPrice;
	    
	    if(userWalletBalance < amountToPay)
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Your wallet balance is not sufficient. Please add money to the wallet",null));
		
		Order order = new Order();
	    
	    order.setDiscountAmount(couponValue);
	    order.setTotalAmount(totalOrderPrice);
	    order.setUser(user);
	    
	    orderRepository.save(order);
	    
	    for(OrderProduct orderProduct:orderProductList) {
	    	orderProduct.setOrder(order);
	    	orderProductRepository.save(orderProduct);
	    }
	    
	    userWalletBalance = couponValue != null ? userWalletBalance - (totalOrderPrice - couponValue):(userWalletBalance - totalOrderPrice);
	    
	    userWallet.setBalance(userWalletBalance);
	    userWalletRepository.save(userWallet);
	    
        return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	public ResponseEntity<ApiResponse> getOrders(Integer userId) {
		List<UserOrder> userOrdersList = orderRepository.findByUserId(userId);
		if(userOrdersList.size() < 1)
    		return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(new ApiResponse("Orders found",userOrdersList));
	}

	public ResponseEntity<ApiResponse> getOrder(Integer orderId) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		
		if(!optionalOrder.isPresent()) {
    		return ResponseEntity.notFound().build();
		}
		
		Order order = optionalOrder.get();
		
		LocalDateTime createdAt = order.getCreatedAt();
		Double orderTotalPrice = order.getTotalAmount();
		Double discount = order.getDiscountAmount();
		List<OrderProduct> orderProducts = order.getOrderProducts();
		
		List<OrderProductDetailDTO> orderProductDetailDTOList = new ArrayList<OrderProductDetailDTO>();
		
		OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
		
		for(OrderProduct orderProduct: orderProducts) {
			
			OrderProductDetailDTO orderProductDetailDTO = new OrderProductDetailDTO();
			
			orderProductDetailDTO.setId(orderProduct.getProduct().getId());
			orderProductDetailDTO.setName(orderProduct.getProduct().getName());
			orderProductDetailDTO.setDescription(orderProduct.getProduct().getDescription());
			orderProductDetailDTO.setPrice(orderProduct.getProduct().getPrice());
			orderProductDetailDTO.setQuantity(orderProduct.getQuantity());
			orderProductDetailDTO.setTotalPrice(orderProduct.getTotalPrice());
			
			orderProductDetailDTOList.add(orderProductDetailDTO);
		}
		orderDetailsDTO.setTotal(orderTotalPrice);
		orderDetailsDTO.setDiscount(discount);
		orderDetailsDTO.setAmountPaid(order.getAmountPaid());
		orderDetailsDTO.setCreatedAt(createdAt);
		orderDetailsDTO.setProducts(orderProductDetailDTOList);
		
		return ResponseEntity.ok(new ApiResponse("Order found for order id "+orderId,orderDetailsDTO));
	}

	public ResponseEntity<ApiResponse> getOrdersByPeriod(String startDate, String endDate) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		
		System.out.println(LocalDate.parse(startDate, dateTimeFormatter));
		return null;
	}
}
