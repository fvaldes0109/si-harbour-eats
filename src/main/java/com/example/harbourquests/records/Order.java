package com.example.harbourquests.records;

import java.util.Date;

import com.example.harbourquests.enums.OrderStatus;

public record Order(Long id, String foodPickupAddress, String deliverAddress, Date creationDate, Date deliveryDate, OrderStatus status) {

}
