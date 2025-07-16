package com.markitos.product.domain.port;

import com.markitos.product.domain.model.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductClientPort {
    
    List<String> getSimilarProducts(String productId);

    CompletableFuture<Product> getProductById(String productId);
}
