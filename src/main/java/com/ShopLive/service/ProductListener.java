package com.ShopLive.service;

import com.ShopLive.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductListener {
    @Autowired
    ProductService productService;

    @KafkaListener(topics = "product-topic", groupId = "product-group")
    public void listen(Product product){
        System.out.println("New Product added : " + product.getName());
        productService.processNewProduct(product);
    }
}
