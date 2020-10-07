package com.systango.shopping.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.systango.shopping.apiresponse.ApiResponse;
import com.systango.shopping.dto.OrderDTO;
import com.systango.shopping.security.CurrentUser;
import com.systango.shopping.service.CustomUserDetails;
import com.systango.shopping.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;

    @Secured("ROLE_USER")
    @PostMapping("/orders")
    public ResponseEntity<ApiResponse> createOrder(@CurrentUser CustomUserDetails currentUser,@Valid @RequestBody OrderDTO orderDTO) {
    	if(orderDTO.getCoupon() != null && orderDTO.getCoupon().isEmpty()) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter valid code");
    	}
    	
    	return orderService.createOrder(currentUser,orderDTO);
    }
    
    @Secured("ROLE_USER")
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse> getOrders(@CurrentUser CustomUserDetails currentUser, @RequestParam(name = "start",required = false) String startDate, @RequestParam(name = "end",required = false) String endDate) {
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MM dd");
		
		System.out.println(LocalDate.parse(startDate, dateTimeFormatter));
    	return orderService.getOrders(currentUser.getId());
    }
    
    @Secured("ROLE_USER")
    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable("id") Integer orderId) {
    	return orderService.getOrder(orderId);
    }
    
    @Secured("ROLE_USER")
    @GetMapping("/ordersa")
    public ResponseEntity<ApiResponse> getOrdersByPeriod(@RequestParam(name = "start") String startDate, @RequestParam(name = "end") String endDate) {
    	return orderService.getOrdersByPeriod(startDate, endDate);
    }
    
}
