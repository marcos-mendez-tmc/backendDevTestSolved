package com.markitos.product.infrastructure.client.dto;

public class SimilarProductResponse {
    private String id;
    private String name;
    private Double price;
    private Boolean availability;

    public SimilarProductResponse(String id, String name, Double price, Boolean availability) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getAvailability() {
        return availability;
    }

}
