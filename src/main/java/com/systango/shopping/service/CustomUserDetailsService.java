package com.systango.shopping.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.*;

import com.systango.shopping.entity.User;
import com.systango.shopping.repository.UserRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
    UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		
        User user = userRepository.findByEmail(usernameOrEmail)
                .orElseThrow(() -> 
                        new UsernameNotFoundException("User not found with email : " + usernameOrEmail)
        );

        return new CustomUserDetails(user);
	}
	
	// This method is used by JWTAuthenticationFilter
    public UserDetails loadUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }
}
