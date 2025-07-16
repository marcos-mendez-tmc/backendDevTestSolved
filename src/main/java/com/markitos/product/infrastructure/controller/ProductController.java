package com.markitos.product.infrastructure.controller;

import com.markitos.product.application.service.ProductService;
import com.markitos.product.domain.model.Product;
import com.markitos.product.infrastructure.client.dto.SimilarProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<List<SimilarProductResponse>> getSimilarProducts(@PathVariable String productId) {
        List<Product> similarProducts = productService.getSimilarProducts(productId);

        List<SimilarProductResponse> response = similarProducts.stream()
            .map(product -> new SimilarProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAvailability()
            ))
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
