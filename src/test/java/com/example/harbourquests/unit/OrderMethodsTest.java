package com.example.harbourquests.unit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.harbourquests.data.entities.Order;
import com.example.harbourquests.enums.OrderStatus;

@SpringBootTest
public class OrderMethodsTest {
    
    @Test
    void orderEquals() {
    
        Order order1 = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);
        Order order2 = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);
        
        assert(order1.equals(order2));
    }

    @Test
    void orderNotEquals() {
    
        Order order1 = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);
        Order order2 = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.completed);
        
        assert(!order1.equals(order2));
    }

    @Test
    void orderNotEqualsNull() {
    
        Order order1 = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);
        
        assert(!order1.equals(null));
    }

    @Test
    void orderHashCode() {
    
        Order order1 = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);
        Order order2 = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);
        
        assert(order1.hashCode() == order2.hashCode());
    }

    @Test
    void orderHashCodeNotEquals() {
    
        Order order1 = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);
        Order order2 = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.completed);
        
        assert(order1.hashCode() != order2.hashCode());
    }

    @Test
    void orderToString() {
    
        Order order = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);
        
        assert(order.toString().equals("Order(id=null, foodPickupAddress=Test street 1, deliverAddress=Test street 2, creationDate=2021-01-01, deliveryDate=2021-01-02, status=inProgress, questCourier=null)"));
    }
}
