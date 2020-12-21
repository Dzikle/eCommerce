package com.eCommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.eCommerce.entity.User;
import com.eCommerce.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/")
	public String Homepage(Model model) {
		
		return "index";
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
	
	@GetMapping("/login")
	public String LoginPage(Model model) {
	return "login";
	}

}
