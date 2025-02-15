package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.models.SortParam;
import com.example.productcatalogservice.models.SortType;
import com.example.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class SearchService implements ISearchService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> searchProducts(String query, int pageNumber, int pageSize, List<SortParam> sortParams) {

        Sort sort = null;

        if(!sortParams.isEmpty()) {
            if(sortParams.get(0).getSortType().equals(SortType.ASC)) {
                sort = Sort.by(sortParams.get(0).getParamName()).ascending();
            } else {
                sort = Sort.by(sortParams.get(0).getParamName()).descending();
            }
        }

        for (int i =1; i< sortParams.size(); i++) {
            if(sortParams.get(i).getSortType().equals(SortType.ASC)) {
                sort = sort.and(Sort.by(sortParams.get(i).getParamName()).ascending());
            } else {
                sort = sort.and(Sort.by(sortParams.get(i).getParamName()).descending());
            }
        }

        return productRepository.findByNameEquals(query, PageRequest.of(pageNumber, pageSize, sort));
    }
}
