package com.eCommerce.entity;

import java.time.LocalTime;
import java.util.Date;
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

import org.springframework.format.annotation.DateTimeFormat;

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
	@ManyToOne(cascade = CascadeType.MERGE)
	public Address adress;
	
	private String requests;
	@Email
	private String email;
	
	private String payment;
	
	private Integer total;
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date date;
	
	public proccesedShoppingCart(List<soldProduct> products, User user, Address adress, String requests,
			@Email String email, String payment, Integer total, Date date) {
		super();
		this.products = products;
		this.user = user;
		this.adress = adress;
		this.requests = requests;
		this.email = email;
		this.payment = payment;
		this.total = total;
		this.date = date;
	}

	

	
	
}
