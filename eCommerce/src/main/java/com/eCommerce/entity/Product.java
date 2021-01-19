package com.eCommerce.entity;

import java.util.EnumSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapKeyEnumerated;
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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;

	@Enumerated
	private Gender gender;
	@Enumerated
	private Category category;
	@MapKeyEnumerated(EnumType.STRING)
	private EnumSet<Color> colors;
	@MapKeyEnumerated(EnumType.STRING)
	private EnumSet<Size> sizes;

	private Integer price;
	@Lob
	@Column(name = "photoFront")
	private String photoFront;
	@Lob
	@Column(name = "photoBack")
	private String photoBack;
	@Lob
	@Column(name = "photoAd")
	private String photoAd;
	
	private boolean stock = true;

	
}