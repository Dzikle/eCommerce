package com.eCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eCommerce.entity.ShoppingCart;
import com.eCommerce.entity.User;
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>{

	ShoppingCart findByUser(User user);

}
