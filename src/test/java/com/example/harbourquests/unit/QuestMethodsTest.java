package com.example.harbourquests.unit;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.harbourquests.data.entities.Quest;

@SpringBootTest
@ActiveProfiles("test")
public class QuestMethodsTest {
    
    @Test
    void questEquals() {
    
        Quest quest1 = new Quest(3600, Map.of(1, 100, 2, 200, 3, 300));
        Quest quest2 = new Quest(3600, Map.of(1, 100, 2, 200, 3, 300));
        
        assert(quest1.equals(quest2));
    }

    @Test
    void questNotEquals() {
    
        Quest quest1 = new Quest(3600, Map.of(1, 100, 2, 200, 3, 300));
        Quest quest2 = new Quest(3600, Map.of(1, 100, 2, 200, 3, 301));
        
        assert(!quest1.equals(quest2));
    }

    @Test
    void questNotEqualsNull() {
    
        Quest quest1 = new Quest(3600, Map.of(1, 100, 2, 200, 3, 300));
        
        assert(!quest1.equals(null));
    }

    @Test
    void questHashCode() {
    
        Quest quest1 = new Quest(3600, Map.of(1, 100, 2, 200, 3, 300));
        Quest quest2 = new Quest(3600, Map.of(1, 100, 2, 200, 3, 300));
        
        assert(quest1.hashCode() == quest2.hashCode());
    }

    @Test
    void questHashCodeNotEquals() {
    
        Quest quest1 = new Quest(3600, Map.of(1, 100, 2, 200, 3, 300));
        Quest quest2 = new Quest(3600, Map.of(1, 100, 2, 200, 3, 301));
        
        assert(quest1.hashCode() != quest2.hashCode());
    }
}
