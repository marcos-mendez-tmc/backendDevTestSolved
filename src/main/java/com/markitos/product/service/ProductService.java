package com.markitos.product.service;

import com.markitos.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:3001";

    public List<Product> getSimilarProducts(String productId) {
        List<Product> similarProducts = new ArrayList<>();

        try {
            ResponseEntity<String[]> response = restTemplate.getForEntity(
                baseUrl + "/product/" + productId + "/similarids", String[].class
            );

            String[] ids = response.getBody();
            if (ids == null) return similarProducts;

            for (String id : ids) {
                try {
                    Product product = restTemplate.getForObject(
                        baseUrl + "/product/" + id,
                        Product.class
                    );
                    if (product != null) {
                        similarProducts.add(product);
                    }
                } catch (HttpClientErrorException.NotFound ex) {
                }
            }

        } catch (HttpClientErrorException.NotFound ex) {
        }

        return similarProducts;
    }
}
