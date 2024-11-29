package com.ShopLive.service;

import com.ShopLive.model.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BidListener {
    @Autowired
    BidService bidService;

    @KafkaListener(topics = "bid-topic", groupId = "bid-group")
    public void listen(Bid bid){
        // Process incoming bid
        System.out.println("Received bid: " + bid.getBidAmount());
        bidService.processBid(bid);
        // Additional logic to update the highest bid and notify users
    }
}

//Listens to bids coming from Kafka and processes them.
