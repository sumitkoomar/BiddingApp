package com.ShopLive.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Product {

    @Id
    private Long id;
    private String name;
    private double StartPrice;
    private double HighestBidPrice;
    private boolean isAvailable;
}
