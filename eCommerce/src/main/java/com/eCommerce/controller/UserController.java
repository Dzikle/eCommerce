package com.eCommerce.controller;



import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String saveUser(@ModelAttribute User user) {
		user.setRole(com.eCommerce.entity.RoleName.ROLE_USER);
		userRepo.save(user);
		return"userForm";
	}
	@GetMapping("/update/form")
	public String updateUserForm(Model model,@AuthenticationPrincipal UsersDetails userD) {
		model.addAttribute("user", userD.getUser());
		return "updateUser";
	}
	@PostMapping("/update/user")
	public String updateUser(@ModelAttribute User user,@AuthenticationPrincipal UsersDetails userD) {
		userD.getUser().setFirstName(user.getFirstName());
		userD.getUser().setLastName(user.getLastName());
		userD.getUser().setEmail(user.getEmail());
		userD.getUser().setAddress(user.getAddress());
		userRepo.save(userD.getUser());
		return "updateUser";
	}
	
	
	@GetMapping("/napravi/me/admin")
	public String saveAdmin(@AuthenticationPrincipal UsersDetails userD) {
		userD.getUser().setRole(com.eCommerce.entity.RoleName.ROLE_ADMIN);
		userRepo.save(userD.getUser());
		return"redirect:/";
	}
	
	@GetMapping("/admin")
	public String adminPanel(@AuthenticationPrincipal UsersDetails userD,Model model) {
		
		model.addAttribute("userList", userRepo.findAll());
		model.addAttribute("user", userD.getUser());
		return "adminPanel";
	}
	@GetMapping("/delete/user/{id}")
	public String deleteUser(@PathVariable Integer id) {
			userRepo.deleteById(id);
		return "redirect:/admin";
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
