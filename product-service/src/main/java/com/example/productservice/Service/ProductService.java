package com.example.productservice.Service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductRespond;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product=Product.builder()
                       .name(productRequest.getName())
                       .descreption(productRequest.getDescription())
                        .price(productRequest.getPrice())
                        .build();
                productRepository.save(product);
   log.info("product   {} is saved", product.getId());
    }
    public List<ProductRespond> getAllProducts(){
        List<Product> product= productRepository.findAll();
      return   product.stream().map(this::mapToProductRespose).toList();
    }
    private ProductRespond mapToProductRespose(Product product){
        return ProductRespond.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescreption())
                .price(product.getPrice())
                .build();
    }
}
