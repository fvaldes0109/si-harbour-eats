package com.example.harbourquests.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.example.harbourquests.data.entities.Order;
import com.example.harbourquests.enums.OrderStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestOrderTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;    

    @Test
    void createOrderWithEmptyBodyShouldFail() {
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/order",
            null, String.class);

        assert(response.getStatusCode().is4xxClientError());
    }

    @Test
    void createOrder() {

        Order order = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);

        ResponseEntity<Order> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/order",
            order, Order.class);

        assert(response.getStatusCode().is2xxSuccessful());

        Order createdOrder = response.getBody();

        assertNotNull(createdOrder);

        assert(createdOrder.getId() != null);
        assert(createdOrder.getFoodPickupAddress().equals("Test street 1"));
        assert(createdOrder.getDeliverAddress().equals("Test street 2"));
        assert(createdOrder.getCreationDate().equals("2021-01-01"));
        assert(createdOrder.getDeliveryDate().equals("2021-01-02"));
        assert(createdOrder.getStatus().equals(OrderStatus.inProgress));
        assert(createdOrder.getQuestCourier() == null);
    }

    @Test
    void getOrderById() {

        Order order = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);
    
        ResponseEntity<Order> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/order",
            order, Order.class);

        assert(response.getStatusCode().is2xxSuccessful());

        Order createdOrder = response.getBody();

        assertNotNull(createdOrder);

        Long orderId = createdOrder.getId();

        ResponseEntity<Order> getOrderResponse = this.restTemplate.getForEntity("http://localhost:" + port + "/api/v1/order/" + orderId,
            Order.class);

        assert(getOrderResponse.getStatusCode().is2xxSuccessful());

        Order fetchedOrder = getOrderResponse.getBody();

        assertNotNull(fetchedOrder);

        assert(fetchedOrder.getId().equals(orderId));
        assert(fetchedOrder.getFoodPickupAddress().equals("Test street 1"));
        assert(fetchedOrder.getDeliverAddress().equals("Test street 2"));
        assert(fetchedOrder.getCreationDate().equals("2021-01-01"));
        assert(fetchedOrder.getDeliveryDate().equals("2021-01-02"));
        assert(fetchedOrder.getStatus().equals(OrderStatus.inProgress));
        assert(fetchedOrder.getQuestCourier() == null);
    }

    @Test
    void getOrders() {

        for (int i = 0; i < 10; i++) {
            Order order = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);
            this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/order", order, Order.class);
        }

        ResponseEntity<Order[]> response = this.restTemplate.getForEntity("http://localhost:" + port + "/api/v1/order",
            Order[].class);

        assert(response.getStatusCode().is2xxSuccessful());

        Order[] orders = response.getBody();

        assertNotNull(orders);

        assert(orders.length >= 10);
    }
}
