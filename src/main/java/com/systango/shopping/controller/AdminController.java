package com.systango.shopping.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systango.shopping.apiresponse.ApiResponse;
import com.systango.shopping.dto.UserUpdateRequestDTO;
import com.systango.shopping.service.UserService;

@RestController
@RequestMapping("/api")
public class AdminController {
	
    @Autowired
    private UserService userService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable("id") Integer userId) {
    	return userService.getUserDetails(userId);
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Integer userId,@Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
    	return userService.updateUser(userId, userUpdateRequestDTO);
    }
    
}
