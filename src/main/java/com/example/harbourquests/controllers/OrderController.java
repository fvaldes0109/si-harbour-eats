package com.example.harbourquests.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.harbourquests.data.entities.Order;
import com.example.harbourquests.data.entities.QuestCourier;
import com.example.harbourquests.data.entities.User;
import com.example.harbourquests.data.repositories.OrderRepository;
import com.example.harbourquests.data.repositories.QuestCourierRepository;
import com.example.harbourquests.data.repositories.UserRepository;
import com.example.harbourquests.enums.OrderStatus;
import com.example.harbourquests.enums.QuestCourierStatus;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestCourierRepository questCourierRepository;

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

    public Order updateOrderStatus(String username, Long orderId, OrderStatus status) {
        
        if (orderId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order ID is required");
        
        User user = userRepository.findByUsername(username);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        Iterable<QuestCourier> questCouriers = questCourierRepository.findByUserAndStatus(username, QuestCourierStatus.inProgress);
        if (!questCouriers.iterator().hasNext()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier doesn't have an active quest");

        Order order = orderRepository.findById(orderId).get();
        if (order == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        if (order.getStatus() == OrderStatus.cancelled || order.getStatus() == OrderStatus.completed)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is already cancelled or delivered");
            
        order.setStatus(status);
        Order savedOrder = orderRepository.save(order);

        QuestCourier questCourier = questCouriers.iterator().next();

        questCourier.addOrder(savedOrder);
        questCourierRepository.save(questCourier);
        return order;
    }
}
