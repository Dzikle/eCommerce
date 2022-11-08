package com.eCommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eCommerce.entity.Product;

public interface ProductRepositoryPaging extends PagingAndSortingRepository<Product	, Integer>{
	@Query("SELECT p FROM Product p WHERE CONCAT(p.name, ' ', p.price, ' ', p.category,' ',p.gender) LIKE %?1%")
	@Transactional(readOnly = true)
	Page<Product> findBySearch(String search, Pageable pageable);

}
