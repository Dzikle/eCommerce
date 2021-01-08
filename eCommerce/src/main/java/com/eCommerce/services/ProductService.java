package com.eCommerce.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.eCommerce.entity.Color;
import com.eCommerce.entity.Product;
import com.eCommerce.entity.ShoppingCart;
import com.eCommerce.entity.User;
import com.eCommerce.entity.soldProduct;
import com.eCommerce.repository.ProductRepository;
import com.eCommerce.repository.ShoppingCartRepository;
import com.eCommerce.repository.SoldProductRepository;
import com.eCommerce.repository.UserRepository;
import com.fasterxml.jackson.databind.ser.std.EnumSetSerializer;

@Service
public class ProductService {

	@Autowired
	ProductRepository prodRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ShoppingCartRepository shopRepo;
	
	@Autowired
	SoldProductRepository soldProdRepo;

	public void AddProduct(Integer id, User user) {
		soldProduct product = soldProdRepo.findById(id).get();
		ShoppingCart cart = shopRepo.findByUser(user);

		if (cart == null) {
			HashMap<soldProduct, Integer> basket = new HashMap<>();
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
		soldProduct product = soldProdRepo.findById(id).get();
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

	public void addPhotos(MultipartFile photoFront, MultipartFile photoBack, MultipartFile photoAd, Product product)
			throws IOException {

	        try {
				product.setPhotoFront(Base64.getEncoder().encodeToString(photoFront.getBytes()));
				product.setPhotoBack(Base64.getEncoder().encodeToString(photoBack.getBytes()));
				product.setPhotoAd(Base64.getEncoder().encodeToString(photoAd.getBytes()));
			} catch (IOException e) {
				e.printStackTrace();
			}
	        prodRepo.save(product);
	}

	public void addSoldProduct(soldProduct sProduct, User user, Integer id) {
		sProduct.setProduct(prodRepo.findById(id).get());
		soldProdRepo.save(sProduct);
		ShoppingCart cart = shopRepo.findByUser(user);

		if (cart == null) {
			HashMap<soldProduct, Integer> basket = new HashMap<>();
			basket.put(sProduct, 1);
			ShoppingCart cartNew = new ShoppingCart(basket, user);
			shopRepo.save(cartNew);
		} else if (!cart.getProduct().containsKey(sProduct)) {
			cart.getProduct().put(sProduct, 1);
			shopRepo.save(cart);
		} else {
			Integer count = cart.getProduct().get(sProduct).intValue();
			cart.getProduct().replace(sProduct, count + 1);
		shopRepo.save(cart);
	}
		
	}

	public List<Product> filterByCategory(String category) {
		List<Product> products = prodRepo.findAll();
		List<Product> filtProducts = new ArrayList<>();
		for (Product product : products) {
			if (product.getCategory().toString().equals(category)) {
				filtProducts.add(product);
			}
		}
		return filtProducts;
	}
}