package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();

    void deleteProduct(Long productId);

    Product getProduct(Long productId);

    Product createProduct(Product product);

    Product updateProduct(Product product, Long id);
}
