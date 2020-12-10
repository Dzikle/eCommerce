package com.eCommerce.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	private Integer id;
	
	private String name;
	
	private Color color;
	
	private Gender gender;
	
	private Integer price;
}
