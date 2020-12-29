package com.eCommerce.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.NaturalId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	 @ElementCollection
	    @CollectionTable(name = "cartBasket", 
	      joinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "id")})
	    @MapKeyColumn(name = "product_id")
	    @Column(name = "quantity")
    private Map<Product, Integer> product;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	public User user;
	public ShoppingCart(Map<Product, Integer> product, User user) {
		super();
		this.product = product;
		this.user = user;
	}
	
	
	
}
