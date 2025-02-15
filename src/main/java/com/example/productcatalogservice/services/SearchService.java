package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class SearchService implements ISearchService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> searchProducts(String query, int pageNumber, int pageSize) {
        return productRepository.findByNameEquals(query, PageRequest.of(pageNumber, pageSize));
    }
}
