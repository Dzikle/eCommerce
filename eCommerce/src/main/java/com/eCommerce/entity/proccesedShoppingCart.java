package com.eCommerce.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class proccesedShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	@ManyToMany(cascade = CascadeType.MERGE)
	public List<soldProduct> products;
	@ManyToOne(fetch = FetchType.EAGER)
	public User user;
	@OneToOne(cascade = CascadeType.ALL)
	public Address adress;
	
	private String requests;
	@Email
	private String email;
	
	private String payment;
	
	private Integer total;

	public proccesedShoppingCart(List<soldProduct> products2, User user, Address adress, String requests,
			@Email String email, String payment, Integer total) {
		super();
		this.products = products2;
		this.user = user;
		this.adress = adress;
		this.requests = requests;
		this.email = email;
		this.payment = payment;
		this.total = total;
	}

	
	
}
