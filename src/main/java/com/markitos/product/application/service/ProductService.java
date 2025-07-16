package com.markitos.product.application.service;

import com.markitos.product.domain.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductAggregator productAggregator;

    public ProductService(ProductAggregator productAggregator) {
        this.productAggregator = productAggregator;
    }

    public List<Product> getSimilarProducts(String productId) {
        return productAggregator.aggregateSimilarProducts(productId);
    }
}
