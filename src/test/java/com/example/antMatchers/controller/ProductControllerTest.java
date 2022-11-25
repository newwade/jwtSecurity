package com.example.antMatchers.controller;

import com.example.antMatchers.entity.Product;
import com.example.antMatchers.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;


    @WithMockUser(value = "sarah", roles = {"ADMIN"})
    @Test
    void shouldReturnProductWhenAddProduct() throws Exception {
        Product product = new Product(1L, "ps5", "next gen console", 50000.0);
        this.mvc.perform(post("/api/v1/products/add").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))).andExpect(
                status().isOk()
        ).andDo(print());
    }

    @WithMockUser(value = "sarah", roles = {"ADMIN"})
    @Test
    void shouldReturnProductWhenGetProduct() throws Exception {
        Product product = new Product(1L, "ps5", "next gen console", 50000.0);
        when(productService.getProduct(product.getProductId())).thenReturn(product);
        this.mvc.perform(get("/api/v1/products/{productId}", 1)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(product.getProductId())
                )
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andDo(print());

    }

    @WithMockUser(value = "sarah", roles = {"ADMIN"})
    @Test
    void shouldReturnListProductWhenGetProduct() throws Exception {
        Product product = new Product(1L, "ps5", "next gen console", 50000.0);
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productService.getAllProducts()).thenReturn(products);
        this.mvc.perform(get("/api/v1/products")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(products.size())
                )
                .andDo(print());

    }

    @WithMockUser(value = "sarah", roles = {"ADMIN"})
    @Test
    void shouldUpdateProduct() throws Exception {
        Product product = new Product(1L, "ps5", "next generation console", 50000.0);
        when(productService.updateProduct(anyLong(), anyString(), anyString(), anyDouble())).thenReturn(product);
        this.mvc.perform(put("/api/v1/products/update").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @WithMockUser(value = "sarah", roles = {"ADMIN"})
    @Test
    void shouldNotUpdateWhenGetProductEmpty() throws Exception{
        Product product = new Product(1L, "ps5", "next generation console", 50000.0);
        when(productService.updateProduct(anyLong(), anyString(), anyString(), anyDouble())).thenReturn(null);
        this.mvc.perform(put("/api/v1/products/update").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    @WithMockUser(value = "sarah", roles = {"ADMIN"})
    @Test
    void shouldThrowWhenProductIdNull() throws Exception{
        Product product = new Product(null, "ps5", "next generation console", 50000.0);
        this.mvc.perform(put("/api/v1/products/update").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }


    @WithMockUser(value = "sarah", roles = {"ADMIN"})
    @Test
    void shouldDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);
        this.mvc.perform(delete("/api/v1/products/{productId}", 1l)).andExpect(status().isOk())
                .andDo(print());
    }

}
