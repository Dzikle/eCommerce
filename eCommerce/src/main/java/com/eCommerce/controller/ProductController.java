package com.eCommerce.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.eCommerce.entity.Product;
import com.eCommerce.repository.ProductRepository;
import com.eCommerce.services.UsersDetails;
@Controller
public class ProductController {
	
	@Autowired
	ProductRepository prodRepo;
	
	
	@GetMapping("product/create")
	public String getProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return"ProductForm";
	}
	@PostMapping("/save")
	public String saveProduct(@ModelAttribute Product product,MultipartFile file) {
		
		  String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	        if(fileName.contains("..")) {
	        	System.out.println("not a valid file");
	        }
	        try {
				product.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		prodRepo.save(product);
		return "ProductForm";
	}
	@GetMapping
	public String updateProduct(Model model,@Param(value = "product") Product product) {
		prodRepo.save(product);
		return"updateForm";
	}
	
	@GetMapping("/products")
	public String productList(Model model) {
		
		List<Product> products = prodRepo.findAll();
		model.addAttribute("products", products);
		
		return "productsList";
	}
	
	@GetMapping("/delete/product/{id}")
	public String deleteProduct(@PathVariable("id") Integer id) {
		prodRepo.deleteById(id);		
		return"redirect:/products";
	}
	

}
