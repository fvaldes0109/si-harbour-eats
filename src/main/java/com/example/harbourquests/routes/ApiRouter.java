package com.example.harbourquests.routes;

import org.springframework.web.bind.annotation.RestController;

import com.example.harbourquests.controllers.OrderController;
import com.example.harbourquests.data.entities.Order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v1")
public class ApiRouter {

    final OrderController orderController;

    public ApiRouter(OrderController orderController) {
        this.orderController = orderController;
    }
    
    @GetMapping("order/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderController.getOrderById(orderId);   
    }
    
    @PostMapping("order")
    public Order createOrder(@RequestBody Order entity) {
        
        return orderController.createOrder(entity);
    }
    
}
