package com.example.productservice.Controller;

import com.example.productservice.Service.ProductService;
import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductRespond;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/b")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){

        productService.createProduct(productRequest);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductRespond> getAllProduct(){
        return productService.getAllProducts();
    }
}
