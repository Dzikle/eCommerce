package com.eCommerce.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.entity.Product;
import com.eCommerce.entity.ShoppingCart;
import com.eCommerce.entity.User;
import com.eCommerce.repository.ProductRepository;
import com.eCommerce.repository.ShoppingCartRepository;
import com.eCommerce.repository.UserRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository prodRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ShoppingCartRepository shopRepo;

	public void AddProduct(Integer id, User user) {
		Product product = prodRepo.findById(id).get();
		ShoppingCart cart = shopRepo.findByUser(user);
		if (cart == null) {
			HashMap<Product, Integer> basket = new HashMap<>();
			basket.put(product, 1);
			ShoppingCart cartNew = new ShoppingCart(basket, user);
			shopRepo.save(cartNew);
		} else if (!cart.getProduct().containsKey(product)) {
			cart.getProduct().put(product, 1);
			shopRepo.save(cart);
		} else {
			Integer count = cart.getProduct().get(product).intValue();
			cart.getProduct().replace(product, count + 1);
			shopRepo.save(cart);
		}
	}

	public void removeProduct(Integer id, User user) {
		Product product = prodRepo.findById(id).get();
		ShoppingCart cart = shopRepo.findByUser(user);

		if (cart.getProduct().get(product) == 1) {
			cart.getProduct().remove(product);
			shopRepo.save(cart);
		} else {
			Integer count = cart.getProduct().get(product).intValue();
			cart.getProduct().replace(product, count - 1);
			shopRepo.save(cart);
		}
	}

	public List<Product> filterByGender(String gender) {
		List<Product> products = prodRepo.findAll();
		List<Product> filtProducts = new ArrayList<>();
		for (Product product : products) {
			if (product.getGender().toString().equals(gender)) {
				filtProducts.add(product);
			}
		}
		return filtProducts;
	}
}