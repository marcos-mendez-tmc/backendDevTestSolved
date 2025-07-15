package com.markitos.product.infrastructure.client;

import com.markitos.product.domain.model.Product;
import com.markitos.product.domain.port.ProductClientPort;
import com.markitos.product.infrastructure.config.ExternalApiProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ExternalProductClient implements ProductClientPort {

    private final RestTemplate restTemplate;
    private final ExternalApiProperties apiProperties;

    public ExternalProductClient(RestTemplate restTemplate, ExternalApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @Override
    public List<String> getSimilarProducts(String productId) {
        try {
            ResponseEntity<String[]> response = restTemplate.getForEntity(
                apiProperties.getBaseUrl() + "/product/" + productId + "/similarids", String[].class
            );
            String[] ids = response.getBody();
            return ids != null ? List.of(ids) : List.of();
        } catch (HttpClientErrorException.NotFound e) {
            return List.of();
        }
    }

    @Async
    @Override
    public CompletableFuture<Product> getProductById(String productId) {
        try {
            Product product = restTemplate.getForObject(
                apiProperties.getBaseUrl() + "/product/" + productId, Product.class
            );
            return CompletableFuture.completedFuture(product);
        } catch (HttpClientErrorException.NotFound e) {
            return CompletableFuture.completedFuture(null);
        }
    }
}
