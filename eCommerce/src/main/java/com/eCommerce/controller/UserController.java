package com.eCommerce.controller;



import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eCommerce.entity.User;
import com.eCommerce.repository.ProccessedCartRepository;
import com.eCommerce.repository.ProductRepository;
import com.eCommerce.repository.ShoppingCartRepository;
import com.eCommerce.repository.UserRepository;
import com.eCommerce.services.ProductService;
import com.eCommerce.services.UsersDetails;

@Controller
public class UserController {
	

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ProductRepository prodRepo;
	
	@Autowired
	ShoppingCartRepository shopCart;
	
	@Autowired
	ProductService prodServ;
	
	@Autowired
	ProccessedCartRepository procCart;
	
	@GetMapping("/")
	public String Homepage(Model model,@AuthenticationPrincipal UsersDetails userD) {
		
		if (userD!=null) {
			if (userD.getUser().getCart()!=null) {
			model.addAttribute("cart", userD.getUser().getCart().getProduct());
			model.addAttribute("total", prodServ.Total(userD.getUser().getCart()));
			}
			model.addAttribute("user", userD.getUser());
		}
		return "homepage";
	}
	
	@GetMapping("/user/form")
	public String getUserForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "userForm";
	}
	
	@PostMapping("/save/user")
	public String saveUser(@ModelAttribute User user,RedirectAttributes redirAttrs) {
		
			
		user.setRole(com.eCommerce.entity.RoleName.ROLE_USER);
		userRepo.save(user);
			redirAttrs.addFlashAttribute("error", "Product removed");
		return"userForm";
	}
	@GetMapping("/napravi/me/admin")
	public String saveAdmin(@AuthenticationPrincipal UsersDetails userD) {
		userD.getUser().setRole(com.eCommerce.entity.RoleName.ROLE_ADMIN);
		userRepo.save(userD.getUser());
		return"redirect:/";
	}
	
	@GetMapping("/login")
	public String LoginPage(Model model) {
	return "login";
	}
	
	@GetMapping("/history")
	public String OrderHistory(Model model,@AuthenticationPrincipal UsersDetails userD) {
		
		if (userD!=null) {
			if (userD.getUser().getCart()!=null) {
			model.addAttribute("cart", userD.getUser().getCart().getProduct());
			model.addAttribute("total", prodServ.Total(userD.getUser().getCart()));
			}
			model.addAttribute("proCart", procCart.findByUser(userD.getUser()));
			model.addAttribute("user", userD.getUser());
		}
		return "orderHistory";
	}
	@GetMapping("/proba")
	public String Homepage2(Model model,@AuthenticationPrincipal UsersDetails userD) {
		
		if (userD!=null) {
			if (userD.getUser().getCart()!=null) {
			model.addAttribute("cart", userD.getUser().getCart().getProduct());
			model.addAttribute("total", prodServ.Total(userD.getUser().getCart()));
			}
			model.addAttribute("user", userD.getUser());
		}
		return "proba";
	}
	
	
}
