package com.eCommerce.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eCommerce.entity.ShoppingCart;
import com.eCommerce.entity.proccesedShoppingCart;
import com.eCommerce.entity.soldProduct;
import com.eCommerce.repository.ProccessedCartRepository;
import com.eCommerce.repository.ShoppingCartRepository;
import com.eCommerce.repository.UserRepository;
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
	
	@PostMapping("/addProduct/{id}")
	public String addProduct(@ModelAttribute soldProduct soldProduct,@PathVariable("id") Integer id,@AuthenticationPrincipal UsersDetails userD,RedirectAttributes redirAttrs) {
		prodServ.addSoldProduct(soldProduct,userD.getUser(),id);
		redirAttrs.addFlashAttribute("success", "Product added");
		return "redirect:/products";
	}
	
	@GetMapping("/add/product/{id}")
	public String addToCart(@PathVariable("id") Integer id,@AuthenticationPrincipal UsersDetails userD,RedirectAttributes redirAttrs) {
		prodServ.AddProduct(id, userD.getUser());
		redirAttrs.addFlashAttribute("success", "Product added");
		return "redirect:/products";
	}
	@GetMapping("/add/page/{id}")
	public String addToCartfromPage(@PathVariable("id") Integer id,@AuthenticationPrincipal UsersDetails userD,RedirectAttributes redirAttrs) {
		prodServ.AddProduct(id, userD.getUser());
		redirAttrs.addFlashAttribute("success", "Product added");
		return "redirect:/show/cart";
	}
	@GetMapping("/remove/{id}")
	public String removeFromCart(@PathVariable("id") Integer id,@AuthenticationPrincipal UsersDetails userD,RedirectAttributes redirAttrs) {
		prodServ.removeProduct(id, userD.getUser());
		redirAttrs.addFlashAttribute("success", "Product removed");
		return "redirect:/show/cart";
	}
	@GetMapping("/show/cart")
	public String showCartPage(@AuthenticationPrincipal UsersDetails userD,Model model,proccesedShoppingCart proccesedShoppingCart) {
		if (userD.getUser().getCart()!=null) {
		ShoppingCart cart= userD.getUser().getCart();
		model.addAttribute("cart", cart.getProduct());
		model.addAttribute("user", userD.getUser());
		model.addAttribute("total", prodServ.Total(cart));
		}
		model.addAttribute("proccesedShoppingCart", proccesedShoppingCart);
		return"cartPage";
	}
	@PostMapping("/checkout")
	public String checkout(@AuthenticationPrincipal UsersDetails userD,@ModelAttribute proccesedShoppingCart proCart,RedirectAttributes redirAttrs) {
		ShoppingCart cart= userD.getUser().getCart();
		if (!cart.getProduct().isEmpty()) {
		shopServ.proccessShoppingCart(userD.getUser(),cart,proCart);
		cart.getProduct().clear();
		shopRepo.save(cart);
		}else {
			redirAttrs.addFlashAttribute("error", "Choose product first!");
		}
		
		return "redirect:/show/cart";
	}
	


}
