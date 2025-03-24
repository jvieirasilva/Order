package com.order.service;

import org.springframework.data.domain.Page;

import com.order.dto.ProductDTO;

import jakarta.validation.Valid;

public  interface ProductService {



    public Page<ProductDTO> findAll(String name, int page, int size);

    public ProductDTO findById(Long id);

    public ProductDTO save(@Valid ProductDTO dto);

    public ProductDTO update(Long id, @Valid ProductDTO dto); 

    public void delete(Long id);

   
}

