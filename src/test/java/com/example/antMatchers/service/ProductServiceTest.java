package com.example.antMatchers.service;

import com.example.antMatchers.entity.Product;
import com.example.antMatchers.repository.ProductRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    private Product product;

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        product = new Product( 1L,"ps5","Next Gen console",50000.0);
    }

    @Test
    void shouldAddProduct(){
        productService.addProduct(product);
        verify(productRepository).save(product);
    }

    @Test
    void shouldReturnListOfProduct(){
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when (productRepository.findAll()).thenReturn(productList);
        List<Product> products =  productService.getAllProducts();
        assertEquals(1,products.size());
    }

    @Test
    void shouldReturnEmptyListOfProducts(){
        when (productRepository.findAll()).thenReturn(new ArrayList<>());
        List<Product> products =  productService.getAllProducts();
        assertEquals(0,products.size());
    }

    @Test
    void shouldReturnProductWhenFindById(){
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product result = productService.getProduct(1L);
      assertEquals(product.getProductId(),result.getProductId());

    }

    @Test
    void shouldReturnNullWhenFindByIdIsEmpty(){
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        Product result = productService.getProduct(1L);
        assertNull(result);
    }

    @Test
    void shouldDeleteProduct(){
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.deleteProduct(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void shouldNotInvokeWhenDeleteProduct(){
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        productService.deleteProduct(1L);
        verify(productRepository,never()).deleteById(1L);
    }

    @Test
    void shouldReturnNullWhenUpdateProductIsEmpty(){
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Product result= productService.updateProduct(product.getProductId(),product.getName(),product.getDescription(),product.getPrice());
        assertNull(result);
    }

    @Test
    void shouldInvokeWhenUpdateProduct(){
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        long prod_id = 1;
        String prod_name = "ps4";
        String prod_desc = "next generation gaming console";
        Double prod_price = 50000.0;
        Product result =  productService.updateProduct(prod_id,prod_name,prod_desc,prod_price);
        verify(productRepository).save(product);
        assertAll("should return updated product",()->{
            assertEquals(result.getProductId(),prod_id);
            assertEquals(result.getName(),prod_name);
            assertEquals(result.getDescription(),prod_desc);
            assertEquals(result.getPrice(),prod_price);
        });
    }







}
