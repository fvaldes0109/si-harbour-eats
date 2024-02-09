package com.example.harbourquests.routes;

import org.springframework.web.bind.annotation.RestController;

import com.example.harbourquests.controllers.CourierController;
import com.example.harbourquests.controllers.OrderController;
import com.example.harbourquests.controllers.QuestController;
import com.example.harbourquests.data.entities.Order;
import com.example.harbourquests.data.entities.Quest;
import com.example.harbourquests.data.entities.QuestCourier;
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
    final CourierController courierController;

    public ApiRouter(OrderController orderController, QuestController questController, CourierController courierController) {
        this.orderController = orderController;
        this.questController = questController;
        this.courierController = courierController;
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

    @PutMapping("order/{username}/{orderId}")
    public Order updateOrderStatus(@PathVariable String username, @PathVariable Long orderId, @RequestBody Order entity) {
        return orderController.updateOrderStatus(username, orderId, entity.getStatus());
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

    @PostMapping("courier/{username}/{questId}")
    public QuestCourier startQuest(@PathVariable String username, @PathVariable Long questId) {
        return courierController.startQuestCourier(username, questId);
    }
    
    @GetMapping("courier/{username}/history")
    public Iterable<QuestCourier> getCourierHistory(@PathVariable String username) {
        return courierController.getHistory(username);
    }

    @GetMapping("courier/{username}/active_quest")
    public QuestCourier getActiveQuest(@PathVariable String username) {
        return courierController.getActiveQuest(username);
    }
}
