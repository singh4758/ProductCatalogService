package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate
                .getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class)
                .getBody();
        return getProducts(fakeStoreProductDtos);
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public Product getProduct(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = restTemplate
                .getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, productId)
                .getBody();

        return getProduct(fakeStoreProductDto);
    }

    @Override
    public Product createProduct(Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = restTemplate
                .postForEntity("https://fakestoreapi.com/products", getFakerProductDto(product), FakeStoreProductDto.class)
                .getBody();

        return getProduct(fakeStoreProductDto);
    }

    private static <T> T nonNull(@Nullable T result) {
        Assert.state(result != null, "No result");
        return result;
    }

    private <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request,
                                               Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return nonNull(restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables));
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto =
                this.putForEntity("https://fakestoreapi.com/products/{id}", getFakerProductDto(product), FakeStoreProductDto.class, productId)
                .getBody();
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
