package com.example.harbourquests.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import com.example.harbourquests.data.entities.Order;
import com.example.harbourquests.data.entities.Quest;
import com.example.harbourquests.data.entities.QuestCourier;
import com.example.harbourquests.data.entities.User;
import com.example.harbourquests.data.repositories.UserRepository;
import com.example.harbourquests.enums.OrderStatus;
import com.example.harbourquests.enums.QuestCourierStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class RestQuestCourierTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void createUser() {
        // In this file, a user should be created per test in here
        userRepository.save(new User("user1"));
        userRepository.save(new User("user2"));
        userRepository.save(new User("user3"));
        userRepository.save(new User("user4"));
    }

    @Test
    void startQuestAndGetActive() {
        
        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        ResponseEntity<Quest> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/quest",
            quest, Quest.class);
        Quest createdQuest = response.getBody();

        assertNotNull(createdQuest);
        Long questId = createdQuest.getId();

        ResponseEntity<QuestCourier> responseCourier = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/courier/user1/" + questId,
            null, QuestCourier.class);

        QuestCourier createdCourier = responseCourier.getBody();

        assertNotNull(createdCourier);

        assert(createdCourier.getId() != null);
        assert(createdCourier.getQuest().getId().equals(questId));
        assert(createdCourier.getUser().getUsername().equals("user1"));
        assert(createdCourier.getQuest().getTimeToCompleteInSeconds() == 3600);
        assert(createdCourier.getQuest().getRewardsByCompletedOrders().equals(Map.of(1, 100, 2, 200)));
        assert(createdCourier.getStatus().equals(QuestCourierStatus.inProgress));

        ResponseEntity<QuestCourier> responseActive = this.restTemplate.getForEntity("http://localhost:" + port + "/api/v1/courier/user1/active_quest",
            QuestCourier.class);

        QuestCourier activeCourier = responseActive.getBody();

        assertNotNull(activeCourier);

        assert(createdCourier.getId() != null);
        assert(createdCourier.getQuest().getId().equals(questId));
        assert(createdCourier.getUser().getUsername().equals("user1"));
        assert(createdCourier.getQuest().getTimeToCompleteInSeconds() == 3600);
        assert(createdCourier.getQuest().getRewardsByCompletedOrders().equals(Map.of(1, 100, 2, 200)));
        assert(createdCourier.getStatus().equals(QuestCourierStatus.inProgress));
    }

    @Test
    void startQuestAndCancel() {

        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        ResponseEntity<Quest> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/quest",
            quest, Quest.class);
        Quest createdQuest = response.getBody();

        assertNotNull(createdQuest);
        Long questId = createdQuest.getId();

        ResponseEntity<QuestCourier> responseCourier = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/courier/user2/" + questId,
            null, QuestCourier.class);

        QuestCourier updatedQuest = responseCourier.getBody();
        assertNotNull(updatedQuest);

        updatedQuest.setStatus(QuestCourierStatus.cancelled);

        HttpEntity<QuestCourier> requestUpdate = new HttpEntity<>(updatedQuest);

        ResponseEntity<QuestCourier> responseUpdatedCourier = this.restTemplate.exchange("http://localhost:" + port + "/api/v1/courier/user2",
            HttpMethod.PUT, requestUpdate, QuestCourier.class);

        QuestCourier cancelledQuest = responseUpdatedCourier.getBody();
        assertNotNull(cancelledQuest);

        assert(cancelledQuest.getId() != null);
        assert(cancelledQuest.getQuest().getId().equals(questId));
        assert(cancelledQuest.getUser().getUsername().equals("user2"));
        assert(cancelledQuest.getQuest().getTimeToCompleteInSeconds() == 3600);
        assert(cancelledQuest.getQuest().getRewardsByCompletedOrders().equals(Map.of(1, 100, 2, 200)));
        assert(cancelledQuest.getStatus().equals(QuestCourierStatus.cancelled));
    }

    @Test
    void startQuestAndCompleteOrder() {

        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        ResponseEntity<Quest> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/quest",
            quest, Quest.class);
        Quest createdQuest = response.getBody();

        assertNotNull(createdQuest);
        Long questId = createdQuest.getId();

        ResponseEntity<QuestCourier> responseCourier = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/courier/user3/" + questId,
            null, QuestCourier.class);

        QuestCourier updatedQuest = responseCourier.getBody();
        assertNotNull(updatedQuest);

        Order order = new Order("Test street 1", "Test street 2", "2021-01-01", "2021-01-02", OrderStatus.inProgress);

        ResponseEntity<Order> responseOrder = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/order",
            order, Order.class);

        Order createdOrder = responseOrder.getBody();
        assertNotNull(createdOrder);

        createdOrder.setStatus(OrderStatus.completed);

        HttpEntity<Order> requestUpdate = new HttpEntity<>(createdOrder);

        ResponseEntity<Order> responseUpdatedOrder = this.restTemplate.exchange("http://localhost:" + port + "/api/v1/order/user3/" + createdOrder.getId(),
            HttpMethod.PUT, requestUpdate, Order.class);

        Order updatedOrder = responseUpdatedOrder.getBody();
        assertNotNull(updatedOrder);

        assert(updatedOrder.getId().equals(createdOrder.getId()));
        assert(updatedOrder.getFoodPickupAddress().equals("Test street 1"));
        assert(updatedOrder.getDeliverAddress().equals("Test street 2"));
        assert(updatedOrder.getCreationDate().equals("2021-01-01"));
        assert(updatedOrder.getDeliveryDate().equals("2021-01-02"));
        assert(updatedOrder.getStatus().equals(OrderStatus.completed));
        assert(updatedOrder.getQuestCourier() != null);

        ResponseEntity<QuestCourier> responseActive = this.restTemplate.getForEntity("http://localhost:" + port + "/api/v1/courier/user3/active_quest",
            QuestCourier.class);

        QuestCourier activeCourier = responseActive.getBody();
        assertNotNull(activeCourier);

        assert(activeCourier.getOrders().size() == 1);
    }

    @Test
    void getCourierHistoryTest() {

        Quest quest1 = new Quest(3600, Map.of(1, 100, 2, 200));
        Quest quest2 = new Quest(3600, Map.of(1, 100, 2, 200));

        ResponseEntity<Quest> responseQuest1 = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/quest",
            quest1, Quest.class);
        ResponseEntity<Quest> responseQuest2 = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/quest",
            quest2, Quest.class);

        Quest createdQuest1 = responseQuest1.getBody();
        Quest createdQuest2 = responseQuest2.getBody();
        assertNotNull(createdQuest1);
        assertNotNull(createdQuest2);

        Long questId1 = createdQuest1.getId();
        Long questId2 = createdQuest2.getId();

        ResponseEntity<QuestCourier> responseQC1 = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/courier/user4/" + questId1,
            null, QuestCourier.class);

        QuestCourier questCourier1 = responseQC1.getBody();
        QuestCourier questCourier2 = responseQC1.getBody();
        assertNotNull(questCourier1);
        assertNotNull(questCourier2);

        questCourier1.setStatus(QuestCourierStatus.completed);
        HttpEntity<QuestCourier> requestUpdate = new HttpEntity<>(questCourier1);

        this.restTemplate.exchange("http://localhost:" + port + "/api/v1/courier/user4",
            HttpMethod.PUT, requestUpdate, QuestCourier.class);

        this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/courier/user4/" + questId2,
            null, QuestCourier.class);

        ResponseEntity<QuestCourier[]> responseHistory = this.restTemplate.getForEntity("http://localhost:" + port + "/api/v1/courier/user4/history",
            QuestCourier[].class);

        QuestCourier[] history = responseHistory.getBody();
        assertNotNull(history);
        assert(history.length == 1);
        assert(history[0].getStatus() == QuestCourierStatus.completed);
    }
}
