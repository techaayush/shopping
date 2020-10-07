package com.systango.shopping.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeListDTO {
	
	private String status;
	
	private List<Map<String,String>> data;
	
}
