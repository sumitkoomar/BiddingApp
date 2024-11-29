package com.ShopLive.service;
import com.ShopLive.model.Bid;
import com.ShopLive.model.Product;
import com.ShopLive.repository.ProductRepository;
import com.ShopLive.websocket.BidWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BidService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    BidWebSocketHandler webSocketHandler;

    public void processBid(Bid bid){
        // Load the product explicitly
        Product product = productRepository.findById(bid.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        if(bid.getBidAmount() > product.getHighestBidPrice()) {
            product.setHighestBidPrice(bid.getBidAmount());
            productRepository.save(product);
            System.out.println("new Highest Bid : " + bid.getBidAmount());
            webSocketHandler.broadCastBidUpdate("new Highest Bid : " + bid.getBidAmount());
        }
        // Logic to evaluate the highest bid and notify all connected client
    }
}
//Handles the bid logic and broadcasts updates to subscribers.