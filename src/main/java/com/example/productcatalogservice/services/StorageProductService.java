package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product1 = optionalProduct.get();
            if(product.getCategory() != null) {
                product1.setCategory(product.getCategory());
            }
            if(product.getPrice() != null) {
                product1.setPrice(product.getPrice());
            }
            if(product.getName() != null) {
                product1.setName(product.getName());
            }
            if(product.getDescription() != null) {
                product1.setDescription(product.getDescription());
            }
            return productRepository.save(product1);  // Save updates
        }
        return null;
    }

    @Override
    public Product getProductDetails(Long productId, Long userId) {
        return null;
    }
}
