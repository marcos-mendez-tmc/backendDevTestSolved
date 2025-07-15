package com.markitos.product.infrastructure.controller;

import com.markitos.product.application.service.ProductService;
import com.markitos.product.domain.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<List<Product>> getSimilarProducts(@PathVariable String productId) {
        List<Product> similarProducts = productService.getSimilarProducts(productId);
        return ResponseEntity.ok(similarProducts);
    }
}
