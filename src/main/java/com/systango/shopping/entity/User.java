package com.systango.shopping.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users")
public class User extends AuditModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1319791855674048858L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="first_name", nullable = false)
	private String firstName;
	
	@Column(name="last_name", nullable = false)
	private String lastName;
	
	@Column(name="email", nullable = false)
	private String email;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="is_active", nullable = false, columnDefinition = "boolean default true")
	private Boolean isActive = true;
	
	@Column(name="is_deleted", nullable = false, columnDefinition = "boolean default false")
	private Boolean isDeleted = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="role_id", nullable = false)
	private Role role;
	
	@OneToOne(mappedBy = "user")
	private UserWallet userWallet;


}
