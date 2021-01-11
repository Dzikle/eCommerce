package com.eCommerce.entity;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

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
	
	 @ElementCollection(fetch = FetchType.EAGER)
	    @CollectionTable(name = "cartBasket", 
	      joinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "id")})
	    @MapKeyColumn(name = "product_id")
	    @Column(name = "quantity")
    private Map<soldProduct, Integer> product;
	
	

	public ShoppingCart(Map<soldProduct, Integer> product, User user) {
		super();
		this.product = product;
	}
	
	
	
}
