package com.example.harbourquests;

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
    void whenIncorrectBodyShouldFail() {
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/order",
            null, String.class);

        assert(response.getStatusCode().is4xxClientError());
    }

    @Test
    void whenCorrectBodyShouldSucceed() {

        Order order = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);

        ResponseEntity<Order> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/order",
            order, Order.class);

        assert(response.getStatusCode().is2xxSuccessful());
    }
}
