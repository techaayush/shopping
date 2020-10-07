package com.systango.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import com.systango.shopping.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    
    public interface UserDetail {
    	Integer getUserId();
    	String getEmail();
    	String getFirstName();
    	String getLastName();
    	Boolean getIsActive();
    	Boolean getIsDeleted();
    }
    
    @Query("SELECT u.id as userId, u.email as email, u.firstName as firstName, u.lastName as lastName, u.isActive as isActive, u.isDeleted as isDeleted from User u WHERE u.id = :userId AND u.isActive = true AND u.isDeleted = false")
    UserDetail findUserDetailById(@Param("userId") Integer userId);

}
