package com.eCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eCommerce.entity.proccesedShoppingCart;

@Repository
public interface ProccessedCartRepository extends JpaRepository<proccesedShoppingCart, Integer> {

}
