package com.markitos.product.application.service;

import com.markitos.product.domain.model.Product;
import com.markitos.product.domain.port.ProductClientPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class ProductAggregator {

    private final ProductClientPort productClientPort;

    public ProductAggregator(ProductClientPort productClientPort) {
        this.productClientPort = productClientPort;
    }

    public List<Product> aggregateSimilarProducts(String productId) {
        List<String> similarIds = productClientPort.getSimilarProducts(productId);

        List<CompletableFuture<Product>> futures = similarIds.stream()
            .map(productClientPort::getProductById)
            .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return futures.stream()
            .map(CompletableFuture::join)
            .filter(Objects::nonNull)
            .toList();
    }
}
