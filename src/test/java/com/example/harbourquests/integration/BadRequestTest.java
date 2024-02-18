package com.example.harbourquests.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.harbourquests.data.entities.Order;
import com.example.harbourquests.data.entities.Quest;
import com.example.harbourquests.data.entities.User;
import com.example.harbourquests.data.repositories.UserRepository;
import com.example.harbourquests.enums.OrderStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class BadRequestTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void createUser() {
        // In this file, a user should be created per test in here
        userRepository.save(new User("user10"));
    }

    @Test
    void updateOrderBadRequest() {

        Order order = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.completed);
        HttpEntity<Order> requestOrder = new HttpEntity<>(order);

        ResponseEntity<Object> response = this.restTemplate.exchange("http://localhost:" + port + "/api/v1/order/user101111/1",
            HttpMethod.PUT, requestOrder, Object.class); 

        assert(response.getStatusCode().is4xxClientError());

        response = this.restTemplate.exchange("http://localhost:" + port + "/api/v1/order/user100/1",
            HttpMethod.PUT, requestOrder, Object.class);

        assert(response.getStatusCode().is4xxClientError());

        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        ResponseEntity<Quest> responseQuest = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/quest", quest, Quest.class);

        @SuppressWarnings("null")
        Long questId = responseQuest.getBody().getId();

        this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/courier/user10/" + questId,
            null, Quest.class);

        response = this.restTemplate.exchange("http://localhost:" + port + "/api/v1/order/user10/99999",
            HttpMethod.PUT, requestOrder, Object.class);

        assert(response.getStatusCode().is4xxClientError());

        ResponseEntity<Order> responseOrder = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/order",
            order, Order.class);

        assertNotNull(responseOrder.getBody());
        @SuppressWarnings("null")
        Long orderId = responseOrder.getBody().getId();

        response = this.restTemplate.exchange("http://localhost:" + port + "/api/v1/order/user10/" + orderId,
            HttpMethod.PUT, requestOrder, Object.class);

        assert(response.getStatusCode().is4xxClientError());
    }
}
