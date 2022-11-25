package com.example.antMatchers.service.impl;

import com.example.antMatchers.entity.Product;
import com.example.antMatchers.repository.ProductRepository;
import com.example.antMatchers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product) ;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long productId) {
       Optional<Product> product =   productRepository.findById(productId);
       if(!product.isEmpty()){
           return product.get();
       }
       return null;
    }

    @Override
    public void deleteProduct(Long productId) {

        Product product = getProduct(productId);
        if(product!=null){
            productRepository.deleteById(productId);
        }

    }

    @Override
    public Product updateProduct(Long productId, String name, String description, Double price) {

        Product product = getProduct(productId);

        if(product ==null){
            return null;
        }

        boolean emptyName = name == null || name.length()<1;
        boolean emptyDescription = description == null || description.length()<1;
        boolean validPrice = price != null && (price.compareTo((double)0) > 0);

        if (!emptyName && !product.getName().equals(name)) {

            product.setName(name);

        }

        if (!emptyDescription && !product.getDescription().equals(description)) {
            product.setDescription(description);
        }

        if(validPrice){
            product.setPrice(price);
        }

        productRepository.save(product);

        return product;
    }


}
