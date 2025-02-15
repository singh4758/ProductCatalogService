package com.example.productcatalogservice.controllers;


import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.dtos.SearchRequestDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @PostMapping
    public Page<Product> searchProducts(@RequestBody SearchRequestDto searchRequestDto) {
        return searchService.searchProducts(searchRequestDto.getQuery(), searchRequestDto.getPageNumber(), searchRequestDto.getPageSize());
    }

    private List<ProductDto> getProductDtoList(List<Product> productList) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product: productList) {
            ProductDto productDto = new ProductDto();
            productDto.setDescription(product.getDescription());
            productDto.setId(product.getId());
            productDto.setId(productDto.getId());
            if(product.getCategory() != null) {
                productDto.setCategory(product.getCategory().getName());
            }
            productDto.setPrice(product.getPrice());
            productDto.setName(product.getName());

            productDtoList.add(productDto);
        }

        return productDtoList;
    }
}
