package com.eCommerce.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eCommerce.entity.Product;

public interface ProductRepositoryPaging extends PagingAndSortingRepository<Product	, Integer>{

}
