package com.ShopLive.controller;

import com.ShopLive.model.Bid;
import com.ShopLive.repository.BidRepository;
import com.ShopLive.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sellLive/bid-api/")
public class BidController {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    @Qualifier("bidKafkaTemplate")
    private KafkaTemplate<String, Bid> kafkaTemplate;

    @Autowired
    BidService bidService;

    @PostMapping("/place-bid")
    public ResponseEntity<String> placeBid(@RequestBody Bid bid) {
        bidRepository.save(bid);
        kafkaTemplate.send("bid-topic", bid);
        return ResponseEntity.ok("Bid is placed successfully");
    }
}
//Handles incoming bids from users.
