package com.eCommerce.controller;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eCommerce.email.Message;
import com.eCommerce.entity.Product;
import com.eCommerce.entity.ShoppingCart;
import com.eCommerce.entity.User;
import com.eCommerce.entity.proccesedShoppingCart;
import com.eCommerce.repository.EmailRepository;
import com.eCommerce.repository.ProccessedCartRepository;
import com.eCommerce.repository.ShoppingCartRepository;
import com.eCommerce.repository.UserRepository;
import com.eCommerce.services.EmailServices;
import com.eCommerce.services.ProductService;
import com.eCommerce.services.ShoppingCartService;
import com.eCommerce.services.UsersDetails;

@Controller
public class CartController {
	
	@Autowired
	ProductService prodServ;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ShoppingCartRepository shopRepo;
	
	@Autowired
	ProccessedCartRepository procCart;
	
	
	@Autowired
	ShoppingCartService shopServ;
	
	
	
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
	public String showCartPage(@AuthenticationPrincipal UsersDetails userD,Model model,proccesedShoppingCart proccesedShoppingCart) {
		User user = userRepo.findByEmail(userD.getUsername());
		ShoppingCart cart = shopRepo.findByUser(user);
		Integer total = 0;
		for (Product product : cart.getProduct().keySet()) {
			total += product.getPrice() * cart.getProduct().get(product);
		}
		model.addAttribute("cart", cart.getProduct());
		model.addAttribute("user", user);
		model.addAttribute("total", total);
		model.addAttribute("proccesedShoppingCart", proccesedShoppingCart);
		
		return"cartPage";
	}
	
	@PostMapping("/custom/{id}")
	public void addCustomQuantity(@RequestParam(value = "integer", required = false) Integer integer,@PathVariable(name = "id") Integer id,@AuthenticationPrincipal UsersDetails userD) {
		User user = userRepo.findByEmail(userD.getUsername());
		ShoppingCart cart = shopRepo.findByUser(user);
	}
	
	@PostMapping("/checkout")
	public String checkout(@AuthenticationPrincipal UsersDetails userD,@ModelAttribute proccesedShoppingCart proCart) {
		User user = userRepo.findByEmail(userD.getUsername());
		ShoppingCart cart = shopRepo.findByUser(user);
		shopServ.proccessShoppingCart(user,cart,proCart);
		cart.getProduct().clear();
		shopRepo.save(cart);
		
		return "redirect:/show/cart";
	}
	


}
