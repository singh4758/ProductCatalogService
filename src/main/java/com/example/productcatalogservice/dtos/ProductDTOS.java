package com.example.productcatalogservice.dtos;

import com.example.productcatalogservice.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTOS {
    private Long id;
    private String name;
    private String description;
    private List<Category> categories;
}
