package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.models.SortParam;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISearchService {
    Page<Product> searchProducts(String query, int pageNumber, int pageSize, List<SortParam> sortParams);
}
