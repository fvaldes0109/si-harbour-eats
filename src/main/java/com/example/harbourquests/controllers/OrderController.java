package com.example.harbourquests.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.harbourquests.data.entities.Order;
import com.example.harbourquests.data.repositories.OrderRepository;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    public @ResponseBody Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    public Order createOrder(Order entity) {

        return orderRepository.save(entity);
    }
}
