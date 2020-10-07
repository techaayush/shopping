package com.systango.shopping.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.systango.shopping.apiresponse.ApiResponse;
import com.systango.shopping.dto.RegisterRequestDTO;
import com.systango.shopping.dto.UserUpdateRequestDTO;
import com.systango.shopping.security.CurrentUser;
import com.systango.shopping.service.CustomUserDetails;
import com.systango.shopping.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
    	return userService.registerUser(registerRequestDTO);
    } 
    
    @Secured("ROLE_USER")
    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUser(@CurrentUser CustomUserDetails currentUser) {
    	return userService.getUserDetails(currentUser.getId());
    }
    
    @Secured("ROLE_USER")
    @PutMapping("/user")
    public ResponseEntity<Object> updateUser(@CurrentUser CustomUserDetails currentUser,@Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
    	return userService.updateUser(currentUser.getId(), userUpdateRequestDTO);
    }
    
    @Secured("ROLE_USER")
    @DeleteMapping("/user")
    public ResponseEntity<Object> deleteUser(@CurrentUser CustomUserDetails currentUser) {
    	return userService.deleteUser(currentUser.getId());
    }
    
    @Secured("ROLE_USER")
    @PutMapping("/users/wallet")
    public ResponseEntity<Object> addMoney(@CurrentUser CustomUserDetails currentUser, @RequestBody Map<String,Double> payload) {
    	if(payload.get("amount") == null)
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "amount is required");
    	
    	if(payload.get("amount") < 100)
    		throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "minimum Rs.100 is required");
    	
    	return userService.addMoney(currentUser.getId(), payload.get("amount"));
    }
    
    @Secured("ROLE_USER")
    @GetMapping("/users/wallet")
    public ResponseEntity<ApiResponse> viewBalance(@CurrentUser CustomUserDetails currentUser) {
    	return userService.viewBalance(currentUser.getId());
    }
    
}
