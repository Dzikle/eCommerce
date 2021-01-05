package com.eCommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.entity.Product;
import com.eCommerce.entity.ShoppingCart;
import com.eCommerce.entity.User;
import com.eCommerce.entity.proccesedShoppingCart;
import com.eCommerce.entity.soldProduct;
import com.eCommerce.repository.ProccessedCartRepository;

@Service
public class ShoppingCartService {
	
	@Autowired
	ProccessedCartRepository procCart;
	
	@Autowired
	EmailServices emailServ;

	public void proccessShoppingCart(User user, ShoppingCart cart, proccesedShoppingCart proCart) {
		Integer total = 0;

		for (Product product : cart.getProduct().keySet()) {
			total += product.getPrice() * cart.getProduct().get(product);
		}
		if (proCart.getAdress() == null) {
			proCart.setAdress(user.getAddress());
		}
		if (proCart.getEmail() == null) {
			proCart.setEmail(user.getEmail());
		}
		List<Product> products = cart.getProduct().keySet().stream().collect(Collectors.toList());
		
		List<soldProduct> soldProducts =

		proccesedShoppingCart userCart = new proccesedShoppingCart(products, user, proCart.getAdress(),
				proCart.getRequests(), proCart.getEmail(), proCart.getPayment(), total);
		procCart.save(userCart);

		emailServ.sendEmailToBuyer(userCart);

	}

}
