package com.eCommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eCommerce.entity.User;
import com.eCommerce.entity.proccesedShoppingCart;

@Repository
public interface ProccessedCartRepository extends JpaRepository<proccesedShoppingCart, Integer> {

	List<proccesedShoppingCart> findByUser(User user);

}
