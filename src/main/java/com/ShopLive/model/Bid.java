package com.ShopLive.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Bid {
    @Id
    private Long id;
    private Long productId;
    private double bidAmount;
    private String bidderName;

}


