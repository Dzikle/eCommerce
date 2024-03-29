package com.eCommerce.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
		if (userD.getUser().getRole().toString().contains("ADMIN")) {
			Product product = new Product();
			model.addAttribute("product", product);
			if (userD != null) {
				model.addAttribute("user", userD.getUser());
			}
			return "ProductForm";
		} else {
			return "redirect:/products";
		}
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

	@GetMapping("/products/update/{id}")
	public String updateProduct(Model model, @PathVariable Integer id) {
		Product product = prodRepo.findById(id).get();
		model.addAttribute("product", product);
		return "UpdateProduct";
	}

	@PostMapping("/product/update/{id}")
	public String updatePet(@ModelAttribute Product product, @PathVariable Integer id) {
		prodRepo.save(product);
		return "redirect:/products";
	}

	@GetMapping("/products")
	public String productList(Model model, @AuthenticationPrincipal UsersDetails userD) {
		model.addAttribute("products", prodRepo.findAll());
		if (userD != null) {
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
//		soldProduct soldProduct = soldProd.findByProduct(prodRepo.findById(id));
//		soldProd.delete(soldProduct);
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
		model.addAttribute("cart", userD.getUser().getCart().getProduct());
		model.addAttribute("total", prodServ.Total(userD.getUser().getCart()));
		model.addAttribute("soldProduct", new soldProduct());
		return "productsList";
	}

	@GetMapping("/filterByCategory")
	public String filterByCategory(Model model, @RequestParam String category,
			@AuthenticationPrincipal UsersDetails userD) {
		model.addAttribute("products", prodServ.filterByCategory(category));
		model.addAttribute("user", userD.getUser());
		model.addAttribute("cart", userD.getUser().getCart().getProduct());
		model.addAttribute("total", prodServ.Total(userD.getUser().getCart()));
		model.addAttribute("soldProduct", new soldProduct());
		return "productsList";
	}

	@GetMapping("/products/grid")
	public String productGrid(Model model, @Param("search") String search) {
		return listByPage(model, 1, "price", "asc", search);
	}

	@GetMapping("/pag/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("search") String search) {

		Page<Product> page = prodServ.listAll(currentPage, sortField, sortDir, search);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		List<Product> listProducts = page.getContent();

		model.addAttribute("listProducts", listProducts);
		model.addAttribute("sortDir", sortDir);
		String revsortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("revsortDir", revsortDir);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("page", page);
		model.addAttribute("search", search);
		return "productGrid";
	}

}
