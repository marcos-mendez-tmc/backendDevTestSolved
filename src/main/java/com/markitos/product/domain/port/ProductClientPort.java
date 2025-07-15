package com.markitos.product.domain.port;

import com.markitos.product.domain.model.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductClientPort {

    CompletableFuture<Product> getProductById(String productId);

    List<String> getSimilarProducts(String productId);
}
