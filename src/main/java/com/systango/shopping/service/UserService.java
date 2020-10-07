package com.systango.shopping.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systango.shopping.apiresponse.ApiResponse;
import com.systango.shopping.dto.RegisterRequestDTO;
import com.systango.shopping.dto.UserUpdateRequestDTO;
import com.systango.shopping.entity.Role;
import com.systango.shopping.entity.User;
import com.systango.shopping.entity.UserWallet;
import com.systango.shopping.repository.RoleRepository;
import com.systango.shopping.repository.UserRepository;
import com.systango.shopping.repository.UserRepository.UserDetail;
import com.systango.shopping.repository.UserWalletRepository;
import com.systango.shopping.utils.RoleName;

@Service
public class UserService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserWalletRepository userWalletRepository;
    
    @Transactional
    public ResponseEntity<ApiResponse> registerUser(RegisterRequestDTO registerRequestDTO) {
    	
    	Boolean isEmailExists = userRepository.existsByEmail(registerRequestDTO.getEmail());
    	if(isEmailExists) {
    		return ResponseEntity.badRequest().body(new ApiResponse("Email address already exists",null));
    	}
    	Role role = roleRepository.findByName(RoleName.ROLE_USER).get();
    	
    	User user = new User();
    	user.setFirstName(registerRequestDTO.getFirstName());
    	user.setLastName(registerRequestDTO.getLastName());
    	user.setEmail(registerRequestDTO.getEmail());
    	user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
    	user.setRole(role);
    	
    	userRepository.save(user);
        logger.info("User registered successfully");

    	return ResponseEntity.status(HttpStatus.CREATED).build();
    }

	public ResponseEntity<ApiResponse> getUserDetails(Integer userId) {
		UserDetail userDetail = userRepository.findUserDetailById(userId);
		if(userDetail==null) {
	        logger.info("User not found");
    		return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new ApiResponse("User found", userDetail));
	}
	
	@Transactional
	public ResponseEntity<Object> updateUser(Integer userId, UserUpdateRequestDTO userUpdateRequestDTO) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(!optionalUser.isPresent()) {
	        logger.info("User not found");
    		return ResponseEntity.notFound().build();
		}
		User user = optionalUser.get();
		user.setFirstName(userUpdateRequestDTO.getFirstName());
		user.setLastName(userUpdateRequestDTO.getLastName());
		if(userUpdateRequestDTO.getIsActive()!=null)
			user.setIsActive(userUpdateRequestDTO.getIsActive());
		
		userRepository.save(user);
        logger.info("User updated");

		return ResponseEntity.noContent().build();
	}
	
	@Transactional
	public ResponseEntity<Object> deleteUser(Integer userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(!optionalUser.isPresent()) {
	        logger.info("User not found");
    		return ResponseEntity.noContent().build();
		}
		User user = optionalUser.get();
		user.setIsDeleted(true);
		
		userRepository.save(user);
        logger.info("User deleted");

		return ResponseEntity.noContent().build();
	}
	
	@Transactional
	public ResponseEntity<Object> addMoney(Integer userId, Double amount) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(!optionalUser.isPresent()) {
	        logger.info("User not found");
    		return ResponseEntity.notFound().build();
		}
		User user = optionalUser.get();
		UserWallet userWallet = null;
				
		userWallet = user.getUserWallet();
		if(userWallet == null) {
			userWallet = new UserWallet();
			userWallet.setUser(user);
		}else {
			amount += userWallet.getBalance();
		}
		userWallet.setBalance(amount);
		
		userWalletRepository.save(userWallet);
        logger.info("Money added to user wallet");
		
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<ApiResponse> viewBalance(Integer userId) {
		Double balance = 0.00;
		UserWallet userWallet = userWalletRepository.findByUserId(userId);
		balance = (userWallet!=null)?userWallet.getBalance():balance;
		
		Map<String, Double> response = new HashMap<String,Double>(); 
		response.put("balance", balance);
		
		return ResponseEntity.ok(new ApiResponse("User wallet balance", response));
	}
}
