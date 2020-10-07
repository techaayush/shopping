package com.systango.shopping.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDTO {
	
	@NotBlank(message = "firstName is required")
    private String firstName;

	@NotBlank(message = "lastName is required")
    private String lastName;
	
	private Boolean isActive;

}
