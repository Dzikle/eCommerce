package com.eCommerce.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	
	private String firstName;
	private String lastName;
	
	

	public String email;
	public String password;
	@OneToOne(cascade = CascadeType.ALL)
	public Address address;
//	@ManyToMany
//	private List<Role> roles;
	@Enumerated
	private RoleName role;
	public User(String firstName, String lastName, String email, String password, Address address, RoleName role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
		this.role = role;
	}
	

}
