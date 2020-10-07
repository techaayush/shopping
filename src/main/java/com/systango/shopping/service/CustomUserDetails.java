package com.systango.shopping.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.systango.shopping.entity.User;

public class CustomUserDetails implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7544436024080025079L;
	
	private Integer id;
	private Boolean isActive;
    private String email;
    private String firstName;
    private String lastName;
	private Boolean isDeleted;


    @JsonIgnore
    private String password;
    
    private List<GrantedAuthority> authorities;
    
    
    public CustomUserDetails() {
		
	}
    
	public CustomUserDetails(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.password = user.getPassword();
		this.isActive = user.getIsActive();
		this.isDeleted = user.getIsDeleted();
		this.authorities = AuthorityUtils.createAuthorityList(user.getRole().getName().name());
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isDeleted==false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActive==true;
	}

}
