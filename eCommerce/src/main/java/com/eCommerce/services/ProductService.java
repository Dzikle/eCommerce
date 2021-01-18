package com.eCommerce.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eCommerce.entity.Product;
import com.eCommerce.entity.ShoppingCart;
import com.eCommerce.entity.User;
import com.eCommerce.entity.soldProduct;
import com.eCommerce.repository.ProductRepository;
import com.eCommerce.repository.ProductRepositoryPaging;
import com.eCommerce.repository.ShoppingCartRepository;
import com.eCommerce.repository.SoldProductRepository;
import com.eCommerce.repository.UserRepository;

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
	
	@Autowired
	ProductRepositoryPaging prodPageRepo;

	public void AddProduct(Integer id, User user) {
		soldProduct product = soldProdRepo.findById(id).get();
		ShoppingCart cart= user.getCart();

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
		ShoppingCart cart= user.getCart();

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
		ShoppingCart cart = user.getCart();

		if (cart == null) {
			HashMap<soldProduct, Integer> basket = new HashMap<>();
			basket.put(sProduct, 1);
			ShoppingCart cartNew = new ShoppingCart(basket, user);
			user.setCart(cartNew);
			userRepo.save(user);
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

	public Integer Total(ShoppingCart cart) {
		Integer total = 0;
		for (soldProduct product : cart.getProduct().keySet()) {
			total += product.getProduct().getPrice() * cart.getProduct().get(product);
		}
		return total;
		
	}
	public Page<Product> listAll(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber-1, 4);
		return prodPageRepo.findAll(pageable);
	}

}