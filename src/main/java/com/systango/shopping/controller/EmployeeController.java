package com.systango.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.systango.shopping.dto.EmployeeListDTO;

@RestController
@RequestMapping("/api/third-party")
public class EmployeeController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/employee")
	public EmployeeListDTO getEmployeesDetails() {
		EmployeeListDTO response = restTemplate.getForObject("http://dummy.restapiexample.com/api/v1/employees", EmployeeListDTO.class);
		return response;
	}

}
