package com.systango.shopping.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequestDTO {
	
	@NotBlank(message = "first name is required")
    private String firstName;

    @NotBlank(message = "last name is required")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "valid email is required")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 12,message = "minimum 12 characters are required for password")
    private String password;
    
}
