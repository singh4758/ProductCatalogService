package com.example.productcatalogservice.services;

import com.example.productcatalogservice.client.FakeStore.FakeStoreApiClient;
import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.dtos.UserDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;
    @Value("${userServiceUrl}")
    private String userServiceUrl;

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreApiClient.getAllProducts();
        return getProducts(fakeStoreProductDtos);
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public Product getProduct(Long productId) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProduct(productId);

        return getProduct(fakeStoreProductDto);
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.createProduct(getFakerProductDto(product));

        return getProduct(fakeStoreProductDto);
    }

    @Override
    public Product getProductDetails(Long productId, Long userId) {
        RestTemplate restTemplate = new RestTemplate();
        UserDto userDto = restTemplate.getForEntity(userServiceUrl+"/users/{uid}", UserDto.class, userId).getBody();
        System.out.println("USER EMAIL"+ userDto.getEmail());
        if(userDto != null) {
            Product product = productRepository.findById(productId).get();
            return product;
        }

        return null;
    }


    @Override
    public Product updateProduct(Product product, Long productId) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.updateProduct(getFakerProductDto(product), productId);
        return getProduct(fakeStoreProductDto);
    }

    private Product getProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);

        return product;
    }

    private List<Product> getProducts(FakeStoreProductDto[] fakeStoreProductDtos) {
        List<Product> productList = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto: fakeStoreProductDtos) {
            Product product = new Product();
            product.setId(fakeStoreProductDto.getId());
            product.setName(fakeStoreProductDto.getTitle());
            product.setPrice(fakeStoreProductDto.getPrice());
            product.setImageUrl(fakeStoreProductDto.getImage());
            product.setDescription(fakeStoreProductDto.getDescription());
            Category category = new Category();
            category.setName(fakeStoreProductDto.getCategory());
            product.setCategory(category);
            productList.add(product);
        }

        return productList;
    }

    private FakeStoreProductDto getFakerProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        fakeStoreProductDto.setDescription((product.getDescription()));
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setTitle(product.getName());

        return fakeStoreProductDto;
    }
}
