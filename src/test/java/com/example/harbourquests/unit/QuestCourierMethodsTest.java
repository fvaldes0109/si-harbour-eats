package com.example.harbourquests.unit;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.harbourquests.data.entities.Order;
import com.example.harbourquests.data.entities.Quest;
import com.example.harbourquests.data.entities.QuestCourier;
import com.example.harbourquests.data.entities.User;
import com.example.harbourquests.enums.OrderStatus;
import com.example.harbourquests.enums.QuestCourierStatus;

@SpringBootTest
public class QuestCourierMethodsTest {
    
    @Test
    void questCourierEquals() {
        
        User user = new User("user1");
        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        QuestCourier questCourier1 = new QuestCourier(user, quest);
        QuestCourier questCourier2 = new QuestCourier(user, quest);
        
        assert(questCourier1.equals(questCourier2));
    }

    @Test
    void questCourierNotEquals() {
        
        User user = new User("user1");
        Quest quest1 = new Quest(3600, Map.of(1, 100, 2, 200));
        Quest quest2 = new Quest(3600, Map.of(1, 100, 2, 300));
        QuestCourier questCourier1 = new QuestCourier(user, quest1);
        QuestCourier questCourier2 = new QuestCourier(user, quest2);
        
        assert(!questCourier1.equals(questCourier2));
    }

    @Test
    void questCourierNotEqualsNull() {
        
        User user = new User("user1");
        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        QuestCourier questCourier = new QuestCourier(user, quest);
        
        assert(!questCourier.equals(null));
    }

    @Test
    void questCourierHashCode() {
        
        User user = new User("user1");
        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        QuestCourier questCourier1 = new QuestCourier(user, quest);
        QuestCourier questCourier2 = new QuestCourier(user, quest);
        
        assert(questCourier1.hashCode() == questCourier2.hashCode());
    }

    @Test
    void questCourierHashCodeNotEquals() {
        
        User user = new User("user1");
        Quest quest1 = new Quest(3600, Map.of(1, 100, 2, 200));
        Quest quest2 = new Quest(3600, Map.of(1, 100, 2, 300));
        QuestCourier questCourier1 = new QuestCourier(user, quest1);
        QuestCourier questCourier2 = new QuestCourier(user, quest2);
        
        assert(questCourier1.hashCode() != questCourier2.hashCode());
    }

    @Test
    void questCourierHashCodeNotEqualsNull() {
        
        User user = new User("user1");
        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        QuestCourier questCourier = new QuestCourier(user, quest);
        
        assert(questCourier.hashCode() != 0);
    }

    @Test
    void questCourierSetId() {
        
        User user = new User("user1");
        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        QuestCourier questCourier = new QuestCourier(user, quest);
        questCourier.setId(99999L);
        assert(questCourier.getId() == 99999L);
    }

    @Test
    void questCourierSetUser() {
            
        User user1 = new User("user1");
        User user2 = new User("user2");
        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        QuestCourier questCourier = new QuestCourier(user1, quest);
        questCourier.setUser(user2);
        assert(questCourier.getUser().equals(user2));
    }

    @Test
    void questCourierSetQuest() {
            
        User user = new User("user1");
        Quest quest1 = new Quest(3600, Map.of(1, 100, 2, 200));
        Quest quest2 = new Quest(3600, Map.of(1, 100, 2, 300));
        QuestCourier questCourier = new QuestCourier(user, quest1);
        questCourier.setQuest(quest2);
        assert(questCourier.getQuest().equals(quest2));
    }

    @Test
    void questCourierSetStatus() {
        
        User user = new User("user1");
        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        QuestCourier questCourier = new QuestCourier(user, quest);
        questCourier.setStatus(QuestCourierStatus.completed);
        assert(questCourier.getStatus().equals(QuestCourierStatus.completed));
    }

    @Test
    void questCourierSetOrders() {

        User user = new User("user1");
        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200));
        QuestCourier questCourier = new QuestCourier(user, quest);
        Order order1 = new Order("foodPickupAddress1", "deliverAddress1", "creationDate1", "deliveryDate1", OrderStatus.inProgress);
        questCourier.setOrders(List.of(order1));
        assert(questCourier.getOrders().size() == 1);
    }
}
