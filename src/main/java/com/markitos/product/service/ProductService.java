package com.markitos.product.service;

import com.markitos.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Async;

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
            if (ids == null || ids.length == 0) return similarProducts;

            List<CompletableFuture<Product>> futures = new ArrayList<>();
            for (String id : ids) {
                futures.add(fetchProductById(id));
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            for (CompletableFuture<Product> future : futures) {
                Product p = future.join();
                if (p != null) {
                    similarProducts.add(p);
                }
            }

        } catch (HttpClientErrorException.NotFound ex) {
        }

        return similarProducts;
    }


    @Async
    public CompletableFuture<Product> fetchProductById(String id) {
        try {
            Product product = restTemplate.getForObject(baseUrl + "/product/" + id, Product.class);
            return CompletableFuture.completedFuture(product);
        } catch (Exception ex) {
            System.out.println(" Error al obtener producto " + id + ": " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }
}


