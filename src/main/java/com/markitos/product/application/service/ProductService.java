package com.markitos.product.application.service;

import com.markitos.product.domain.model.Product;
import com.markitos.product.domain.port.ProductClientPort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    private final ProductClientPort productClientPort;

    public ProductService(ProductClientPort productClientPort) {
        this.productClientPort = productClientPort;
    }

    public List<Product> getSimilarProducts(String productId) {
        List<String> similarIds = productClientPort.getSimilarProducts(productId);
        List<CompletableFuture<Product>> futures = new ArrayList<>();

        for (String id : similarIds) {
            futures.add(fetchProductByIdAsync(id));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<Product> result = new ArrayList<>();
        for (CompletableFuture<Product> future : futures) {
            Product product = future.join();
            if (product != null) {
                result.add(product);
            }
        }

        return result;
    }

    @Async
    public CompletableFuture<Product> fetchProductByIdAsync(String id) {
        try {
            Product product = productClientPort.getProductById(id).join(); 

            return CompletableFuture.completedFuture(product);
        } catch (Exception e) {
            System.out.println("Error fetching product with id " + id + ": " + e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }
}
