package com.example.antMatchers.service;


import com.example.antMatchers.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product addProduct(Product product);

    List<Product> getAllProducts();

    Product getProduct(Long productId);

    void deleteProduct(Long productId);

    Product updateProduct(Long productId, String name, String description, Double price);
}
