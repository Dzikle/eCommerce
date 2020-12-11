package com.eCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eCommerce.entity.ShoppingCart;
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>{

}
