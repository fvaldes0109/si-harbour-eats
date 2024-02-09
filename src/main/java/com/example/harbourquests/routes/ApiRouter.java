package com.example.harbourquests.routes;

import org.springframework.web.bind.annotation.RestController;

import com.example.harbourquests.controllers.OrderController;
import com.example.harbourquests.controllers.QuestController;
import com.example.harbourquests.data.entities.Order;
import com.example.harbourquests.data.entities.Quest;
import com.example.harbourquests.enums.OrderStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("api/v1")
public class ApiRouter {

    final OrderController orderController;
    final QuestController questController;

    public ApiRouter(OrderController orderController, QuestController questController) {
        this.orderController = orderController;
        this.questController = questController;
    }
    
    // TODO: Add query parameters filters
    @GetMapping("order")
    public Iterable<Order> getOrders(@RequestParam(required = false) OrderStatus status) {
        return orderController.getOrders(status);
    }
    
    @GetMapping("order/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderController.getOrderById(orderId);   
    }
    
    @PostMapping("order")
    public Order addOrder(@RequestBody Order entity) {
        return orderController.createOrder(entity);
    }

    // TODO: If there is an active quest and the order was completed, add 1 to the QuestCourier
    @PutMapping("order/{id}")
    public Order updateOrderStatus(@PathVariable Long id, @RequestBody Order entity) {
        return orderController.updateOrderStatus(id, entity.getStatus());
    }

    @GetMapping("quest")
    public Iterable<Quest> getQuests() {
        return questController.getQuests();
    }

    @PostMapping("quest")
    public Quest addQuest(@RequestBody Quest entity) {
        return questController.createQuest(entity);
    }

    @PutMapping("quest/{questId}")
    public Quest updateQuest(@PathVariable Long questId, @RequestBody Quest entity) {
        return questController.updateQuest(questId, entity);
    }

    @GetMapping("quest/{questId}")
    public Quest getQuestById(@PathVariable Long questId) {
        return questController.getQuestById(questId);
    }
}
