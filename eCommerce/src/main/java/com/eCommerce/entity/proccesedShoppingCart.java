package com.eCommerce.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
public class proccesedShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Product> products;
	@ManyToOne(cascade = CascadeType.ALL)
	public User user;
	@OneToOne(cascade = CascadeType.ALL)
	public Address adress;
	
}
