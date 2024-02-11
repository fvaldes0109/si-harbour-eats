package com.example.harbourquests.unit;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.harbourquests.data.entities.QuestCourier;
import com.example.harbourquests.data.entities.User;

@SpringBootTest
public class UserMethodsTest {
    
    @Test
    void userEquals() {
        
        User user1 = new User("user1");
        User user2 = new User("user1");
        
        assert(user1.equals(user2));
    }

    @Test
    void userNotEquals() {
        
        User user1 = new User("user1");
        User user2 = new User("user2");
        
        assert(!user1.equals(user2));
    }

    @Test
    void userNotEqualsNull() {
        
        User user1 = new User("user1");
        
        assert(!user1.equals(null));
    }

    @Test
    void userHashCode() {
        
        User user1 = new User("user1");
        User user2 = new User("user1");
        
        assert(user1.hashCode() == user2.hashCode());
    }

    @Test
    void userHashCodeNotEquals() {
        
        User user1 = new User("user1");
        User user2 = new User("user2");
        
        assert(user1.hashCode() != user2.hashCode());
    }

    @Test
    void userHashCodeNotEqualsNull() {
        
        User user1 = new User("user1");
        
        assert(user1.hashCode() != 0);
    }

    @Test
    void userToString() {
        
        User user1 = new User("user1");
        System.out.println(user1.toString());
        assert(user1.toString().equals("User(id=null, username=user1, questCouriers=null)"));
    }

    @Test
    void userSetId() {
        
        User user1 = new User("user1");
        user1.setId(99999L);
        assert(user1.getId() == 99999L);
    }

    @Test
    void userSetUsername() {
        
        User user1 = new User("user1");
        user1.setUsername("user2");
        assert(user1.getUsername().equals("user2"));
    }

    @Test
    void userSetQuestCouriers() {
        
        User user1 = new User("user1");
        QuestCourier questCourier = new QuestCourier();

        user1.setQuestCouriers(List.of(questCourier));

        assert(user1.getQuestCouriers().size() == 1);
    }
}
