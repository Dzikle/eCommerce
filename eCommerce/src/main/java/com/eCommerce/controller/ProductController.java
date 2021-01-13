package com.eCommerce.controller;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.eCommerce.entity.Product;
import com.eCommerce.entity.ShoppingCart;
import com.eCommerce.entity.soldProduct;
import com.eCommerce.repository.ProductRepository;
import com.eCommerce.repository.ShoppingCartRepository;
import com.eCommerce.repository.SoldProductRepository;
import com.eCommerce.repository.UserRepository;
import com.eCommerce.services.ProductService;
import com.eCommerce.services.UsersDetails;

@Controller
public class ProductController {

	@Autowired
	ProductRepository prodRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ProductService prodServ;

	@Autowired
	SoldProductRepository soldProd;

	@Autowired
	ShoppingCartRepository shopRepo;

	@GetMapping("product/create")
	public String getProductForm(Model model, @AuthenticationPrincipal UsersDetails userD) {
		
		Product product = new Product();
		model.addAttribute("product", product);
		if(userD!=null){
		model.addAttribute("user", userD.getUser());
		}
		return "ProductForm";
	}

	@PostMapping("/save")
	public String saveProduct(@ModelAttribute Product product, MultipartFile file, MultipartFile file1,
			MultipartFile file2) {
		try {
			prodServ.addPhotos(file, file1, file2, product);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:product/create";
	}
	@GetMapping
	public String updateProduct(Model model, @Param(value = "product") Product product) {
		prodRepo.save(product);
		return "updateForm";
	}
	@GetMapping("/products")
	public String productList(Model model, @AuthenticationPrincipal UsersDetails userD) {
		List<Product> products = prodRepo.findAll();
		model.addAttribute("products", products);
		if(userD!=null) {
		ShoppingCart cart = userD.getUser().getCart();
		if (cart != null) {
			model.addAttribute("cart", cart.getProduct());
			model.addAttribute("total", prodServ.Total(cart));
		}
		model.addAttribute("user", userD.getUser());
		}
		model.addAttribute("soldProduct", new soldProduct());
		return "productsList";
	}

	@GetMapping("/delete/product/{id}")
	public String deleteProduct(@PathVariable("id") Integer id) {
		soldProduct soldProduct = soldProd.findByProduct(prodRepo.findById(id));
		soldProd.delete(soldProduct);
		prodRepo.deleteById(id);
		return "redirect:/products";
	}
	@PostMapping("/soldProduct")
	public void soldProduct(@ModelAttribute soldProduct soldProduct) {
		soldProd.save(soldProduct);
	}
	@GetMapping("/filterByGernder")
	public String filterByGender(Model model, @RequestParam String gender,
			@AuthenticationPrincipal UsersDetails userD) {
		model.addAttribute("products", prodServ.filterByGender(gender));
		model.addAttribute("user", userD.getUser());
		ShoppingCart cart = userD.getUser().getCart();
		model.addAttribute("cart", cart.getProduct());
		model.addAttribute("total", prodServ.Total(cart));
		model.addAttribute("soldProduct", new soldProduct());
		return "productsList";
	}

	@GetMapping("/filterByCategory")
	public String filterByCategory(Model model, @RequestParam String category,
			@AuthenticationPrincipal UsersDetails userD) {
		model.addAttribute("products", prodServ.filterByCategory(category));
		model.addAttribute("user", userD.getUser());
		ShoppingCart cart = userD.getUser().getCart();
		model.addAttribute("cart", cart.getProduct());
		model.addAttribute("total", prodServ.Total(cart));
		model.addAttribute("soldProduct", new soldProduct());
		return "productsList";
	}
}
