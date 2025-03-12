package com.example.productcatalogservice.client.FakeStore;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FakeStoreApiClient {
    
    @Value("${fakeStoreUrl}")
    private String fakeStoreUrl;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductDto[] getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate
                .getForEntity(fakeStoreUrl+"/products", FakeStoreProductDto[].class)
                .getBody();
    }

    public FakeStoreProductDto getProduct(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate
                .getForEntity(fakeStoreUrl+"/products/{id}", FakeStoreProductDto.class, productId)
                .getBody();
    }

    public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate
                .postForEntity(fakeStoreUrl+"/products", fakeStoreProductDto, FakeStoreProductDto.class)
                .getBody();
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

    public FakeStoreProductDto updateProduct(FakeStoreProductDto fakeStoreProductDto, Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return this.putForEntity(fakeStoreUrl+"/products/{id}", fakeStoreProductDto, FakeStoreProductDto.class, productId)
                        .getBody();
    }
}
