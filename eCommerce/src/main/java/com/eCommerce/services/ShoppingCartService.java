package com.eCommerce.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.entity.ShoppingCart;
import com.eCommerce.entity.User;
import com.eCommerce.entity.proccesedShoppingCart;
import com.eCommerce.entity.soldProduct;
import com.eCommerce.repository.ProccessedCartRepository;
import com.eCommerce.repository.SoldProductRepository;

@Service
public class ShoppingCartService {

	@Autowired
	ProccessedCartRepository procCart;

	@Autowired
	EmailServices emailServ;
	
	@Autowired
	SoldProductRepository soldProdRepo;

	public void proccessShoppingCart(User user, ShoppingCart cart, proccesedShoppingCart proCart) {
		Integer total = 0;
		for (soldProduct product : cart.getProduct().keySet()) {
			total += product.getProduct().getPrice() * cart.getProduct().get(product);
			product.setQuantity(cart.getProduct().get(product));
			soldProdRepo.save(product);
		}
		if (proCart.getAdress().getCity().equals("")) {
			proCart.setAdress(user.getAddress());
		}
		if (proCart.getEmail().equals("")) {
			proCart.setEmail(user.getEmail());
		}
		
		List<soldProduct> products = cart.getProduct().keySet().stream().collect(Collectors.toList());
		proccesedShoppingCart userCart = new proccesedShoppingCart(products, user, proCart.getAdress(),
				proCart.getRequests(), proCart.getEmail(), proCart.getPayment(), total,new Date(System.currentTimeMillis()));
		procCart.save(userCart);
		emailServ.sendEmailToBuyer(userCart);
	}
}
