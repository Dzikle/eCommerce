package com.eCommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eCommerce.entity.Product;
import com.eCommerce.entity.soldProduct;

@Repository
public interface SoldProductRepository extends JpaRepository<soldProduct, Integer> {

	soldProduct findByProduct(Optional<Product> findById);

}
