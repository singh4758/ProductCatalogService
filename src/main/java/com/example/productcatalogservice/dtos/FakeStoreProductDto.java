package com.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private Integer price;
    private String description;
    private String imageUrl;
    private String category;
    private FakeStoreRatingDto rating;
}
