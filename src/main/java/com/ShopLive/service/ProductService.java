package com.ShopLive.service;

import com.ShopLive.model.Product;
import com.ShopLive.websocket.ProductWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductWebSocketHandler webSocketHandler;

    public void processNewProduct(Product product){
        webSocketHandler.broadcastProductUpdate(product);
    }
}
