package com.systango.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systango.shopping.entity.UserWallet;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Integer> {
	
	UserWallet findByUserId(Integer userId);
	
}
