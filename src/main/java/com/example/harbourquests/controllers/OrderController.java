package com.example.harbourquests.controllers;

import java.util.Date;

import org.springframework.web.bind.annotation.RestController;

import com.example.harbourquests.enums.OrderStatus;
import com.example.harbourquests.records.Order;

@RestController
public class OrderController {

    public Order getOrderById(Long orderId) {
        return new Order(1L, "1234 Main St", "5678 Elm St", new Date(), new Date(), OrderStatus.inProgress);
    }
}
