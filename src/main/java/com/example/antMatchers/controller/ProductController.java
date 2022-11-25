package com.example.antMatchers.controller;

import com.example.antMatchers.entity.Product;
import com.example.antMatchers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable("productId") Long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String,Object>> getProduct(@RequestBody Product product) {
        try {
            if (product.getProductId() == null) {
                throw new IllegalArgumentException("product id cannot be empty");
            }
            Product updatedProduct = productService.updateProduct(product.getProductId(), product.getName(), product.getDescription(), product.getPrice());
            Map<String,Object> response = new HashMap<>();
            if (updatedProduct==null){
                response.put("success",false);
                response.put("product",product);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("success",true);
            response.put("product",product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }
    }
}
