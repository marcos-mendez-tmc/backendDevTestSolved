package com.markitos.product.service;


import com.markitos.product.model.Product;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductCacheService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:3001";

    @Cacheable("products")
    public Product fetchProductByIdSync(String id) {
        return restTemplate.getForObject(baseUrl + "/product/" + id, Product.class);
    }
}