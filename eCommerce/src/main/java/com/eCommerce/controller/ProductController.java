package com.eCommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.eCommerce.entity.Product;
import com.eCommerce.repository.ProductRepository;
@Controller
public class ProductController {
	
	@Autowired
	ProductRepository prodRepo;
	
	
	@GetMapping("product/create")
	public String getProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return"productForm";
	}
	@PostMapping("product/save")
	public String saveProduct(@ModelAttribute Product product) {
		prodRepo.save(product);
		return "redirect:productForm";
	}
	@GetMapping
	public String updateProduct(Model model,@Param(value = "product") Product product) {
		prodRepo.save(product);
		return"updateForm";
	}
	

}
