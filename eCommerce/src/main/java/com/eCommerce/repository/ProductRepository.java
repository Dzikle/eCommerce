package com.eCommerce.repository;

import java.util.List;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eCommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	void findByName(String product);
	
//	@Query(value="SELECT p FROM Product p WHERE p.gender=:gender")
//	@Transactional(readOnly = true)
	List<Product> findByGender(@Param("gender")  String gender);

}
