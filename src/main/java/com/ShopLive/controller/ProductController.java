package com.ShopLive.controller;

import com.ShopLive.model.Product;
import com.ShopLive.repository.ProductRepository;
import com.ShopLive.service.ProductService;
import com.ShopLive.websocket.ProductWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sellLive/product-api/")
public class ProductController {

    @Autowired
    @Qualifier("productKafkaTemplate")
    private KafkaTemplate<String, Product> kafkaTemplate;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @PostMapping("/create-product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product.setAvailable(true);
        productRepository.save(product);
        kafkaTemplate.send("product-topic", product);
        return ResponseEntity.ok(product);
    }
}

//Manages the creation of products for bidding.
