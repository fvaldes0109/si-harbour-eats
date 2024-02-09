package com.example.harbourquests.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.harbourquests.data.entities.Order;
import com.example.harbourquests.data.repositories.OrderRepository;
import com.example.harbourquests.enums.OrderStatus;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    public Iterable<Order> getOrders(OrderStatus status) {
        
        return orderRepository.findAll();
    }

    public @ResponseBody Order getOrderById(Long orderId) {

        if (orderId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order ID is required");

        return orderRepository.findById(orderId).get();
    }

    public Order createOrder(Order entity) {

        if (entity == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is required");

        return orderRepository.save(entity);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        
        if (orderId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order ID is required");

        Order order = orderRepository.findById(orderId).get();
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
