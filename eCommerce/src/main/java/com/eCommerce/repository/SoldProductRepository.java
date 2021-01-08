package com.eCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eCommerce.entity.soldProduct;

@Repository
public interface SoldProductRepository extends JpaRepository<soldProduct, Integer> {

}
