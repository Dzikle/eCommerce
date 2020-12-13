package com.eCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eCommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	void findByName(String product);

}
