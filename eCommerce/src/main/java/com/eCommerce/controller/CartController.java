package com.eCommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.eCommerce.repository.ProductRepository;

@Controller
public class CartController {
	
	@Autowired
	ProductRepository prodRepo;
	
	@PostMapping
	public String addToCart(@ModelAttribute String product) {
		prodRepo.findByName(product);
		
		return "";
	}

}
