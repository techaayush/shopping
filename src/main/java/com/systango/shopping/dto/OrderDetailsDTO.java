package com.systango.shopping.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsDTO {
	
	private Double total;
	
	private Double discount;
	
	private Double amountPaid;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm a")
	private LocalDateTime createdAt;
	
	private List<OrderProductDetailDTO> products;
	
}
