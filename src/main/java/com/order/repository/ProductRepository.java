package com.order.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.order.model.Product;

@Repository
public interface ProductRepository extends  JpaRepository<Product, Long>{
	
	@Query(" Select s from Product s ")
    List<Product> productALL(Pageable pageable);

	Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
	

	
	
	
	

}
