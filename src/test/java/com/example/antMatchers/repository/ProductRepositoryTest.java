package com.example.antMatchers.repository;

import com.example.antMatchers.entity.Product;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.NoSuchElementException;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setup(){
        product = new Product( 1L,"ps5","Next Gen console",50000.0);
    }

    @Test
    @DisplayName("It should return product when calling save method")
    void shouldReturnProductWhenMethodSave(){
        Product result = productRepository.save(product);
        assertEquals(product.toString(),result.toString());
    }

    @Test
    @DisplayName("It should return product when using findById method")
    void shouldReturnProductWhenMethodFindById(){
        Product save_result = productRepository.save(product);
        Product findById_result = productRepository.findById(save_result.getProductId()).orElseThrow();
        assertEquals(save_result.getProductId(),findById_result.getProductId());
    }

    @Test
    @DisplayName("It should throw NoSuchElement Exception when using findById with invalid id ")
    void shouldThrowWhenMethodFindByIdIsEmpty(){

        assertThrows(NoSuchElementException.class,()->{
           productRepository.findById((long)2).orElseThrow();
        });

    }



}
