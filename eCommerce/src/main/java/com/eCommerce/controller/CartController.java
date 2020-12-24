package com.eCommerce.controller;


import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eCommerce.entity.Product;
import com.eCommerce.entity.ShoppingCart;
import com.eCommerce.entity.User;
import com.eCommerce.repository.ShoppingCartRepository;
import com.eCommerce.repository.UserRepository;
import com.eCommerce.services.ProductService;
import com.eCommerce.services.UsersDetails;

@Controller
public class CartController {
	
	@Autowired
	ProductService prodServ;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ShoppingCartRepository shopRepo;
	
	
	@GetMapping("/add/product/{id}")
	public String addToCart(@PathVariable("id") Integer id,@AuthenticationPrincipal UsersDetails userD,RedirectAttributes redirAttrs) {
		User user = userRepo.findByEmail(userD.getUsername());
		prodServ.AddProduct(id, user);
		redirAttrs.addFlashAttribute("success", "Product added");
		return "redirect:/products";
	}
	@GetMapping("/add/page/{id}")
	public String addToCartfromPage(@PathVariable("id") Integer id,@AuthenticationPrincipal UsersDetails userD,RedirectAttributes redirAttrs) {
		User user = userRepo.findByEmail(userD.getUsername());
		prodServ.AddProduct(id, user);
		redirAttrs.addFlashAttribute("success", "Product added");
		return "redirect:/show/cart";
	}
	@GetMapping("/remove/{id}")
	public String removeFromCart(@PathVariable("id") Integer id,@AuthenticationPrincipal UsersDetails userD,RedirectAttributes redirAttrs) {
		User user = userRepo.findByEmail(userD.getUsername());
		prodServ.removeProduct(id, user);
		redirAttrs.addFlashAttribute("success", "Product removed");
		return "redirect:/show/cart";
	}
	
	
	@GetMapping("/show/cart")
	public String showCartPage(@AuthenticationPrincipal UsersDetails userD,Model model) {
		User user = userRepo.findByEmail(userD.getUsername());
		ShoppingCart cart = shopRepo.findByUser(user);
		Integer total = 0;
		for (Product product : cart.getProduct().keySet()) {
			total += product.getPrice() * cart.getProduct().get(product);
		}
		
		model.addAttribute("cart", cart.getProduct());
		model.addAttribute("user", user);
		model.addAttribute("total", total);
		
		return"cartPage";
	}
	
	@PostMapping("/custom/{id}")
	public void addCustomQuantity(@RequestParam(value = "integer", required = false) Integer integer,@PathVariable(name = "id") Integer id,@AuthenticationPrincipal UsersDetails userD) {
		User user = userRepo.findByEmail(userD.getUsername());
		ShoppingCart cart = shopRepo.findByUser(user);
		
	}
}
