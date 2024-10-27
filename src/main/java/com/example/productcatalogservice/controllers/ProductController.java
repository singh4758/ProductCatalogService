package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProduct() {
        List<Product> productList = productService.getAllProducts();
        return productList;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

            if(productId < 1) {
                headers.add("called by", "pagal frontend");
                throw new IllegalArgumentException("ProductId is not valid");
            }
            headers.add("called by", "smart frontend");
            Product product = productService.getProduct(productId);
            return new ResponseEntity<>(getProductDto(product), headers, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody Product product) {
        return getProductDto(productService.createProduct(product));
    }

    @PutMapping("/products/{id}")
    public ProductDto updateProduct(@PathVariable("id") Long id,@RequestBody Product product) {
        return getProductDto(productService.updateProduct(product, id));
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
    }


    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setId(product.getId());
        productDto.setId(productDto.getId());
        if(product.getCategory() != null) {
            productDto.setCategory(product.getCategory().getName());
        }
        productDto.setPrice(product.getPrice());
        productDto.setName(product.getName());

        return productDto;
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
