package com.systango.shopping.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	
    @NotBlank(message="email is required")
    private String email;

    @NotBlank(message="password is required")
    private String password;

    
}
