package com.eCommerce;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eCommerce.entity.User;
import com.eCommerce.services.UsersService;

@Controller
@RequestMapping("/signUpForm")
public class ViewController {
	
	@Autowired
	UsersService usersService;
	
	
	@ModelAttribute("user")
	public User userDto() {
		return new User();
	}
	
	
	@GetMapping
	public String showSignUpForm(Model model) {
		
	    return "userForm";
	}
	
	@PostMapping
	public String registerUser(@ModelAttribute("user") User userDto) {
		
		usersService.save(userDto);
		
		return  "redirect:/userForm?success" ;
	}
	
	@GetMapping("/login")
	public String LoginPage() {
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		if (authentication==null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		
		return"redirect:/index";
	}
}
