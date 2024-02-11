package com.example.harbourquests.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.example.harbourquests.data.entities.Quest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestQuestTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createQuestWithEmptyBodyShouldFail() {
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/quest",
            null, String.class);

        assert(response.getStatusCode().is4xxClientError());
    }

    @Test
    void createQuest() {

        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200, 3, 300));

        ResponseEntity<Quest> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/quest",
            quest, Quest.class);

        assert(response.getStatusCode().is2xxSuccessful());

        Quest createdQuest = response.getBody();

        assertNotNull(createdQuest);

        assert(createdQuest.getId() != null);
        assert(createdQuest.getTimeToCompleteInSeconds() == 3600);
        assert(createdQuest.getRewardsByCompletedOrders().equals(Map.of(1, 100, 2, 200, 3, 300)));
    }

    @Test
    void getQuests() {

        ResponseEntity<Quest[]> response = this.restTemplate.getForEntity("http://localhost:" + port + "/api/v1/quest",
            Quest[].class);

        assert(response.getStatusCode().is2xxSuccessful());

        Quest[] quests = response.getBody();

        assertNotNull(quests);
    }

    @Test
    void getQuestById() {

        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200, 3, 300));

        ResponseEntity<Quest> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/quest",
            quest, Quest.class);

        assert(response.getStatusCode().is2xxSuccessful());

        Quest createdQuest = response.getBody();
        assertNotNull(createdQuest);

        Long questId = createdQuest.getId();

        ResponseEntity<Quest> getQuestResponse = this.restTemplate.getForEntity("http://localhost:" + port + "/api/v1/quest/" + questId,
            Quest.class);

        assert(getQuestResponse.getStatusCode().is2xxSuccessful());

        Quest fetchedQuest = getQuestResponse.getBody();
        assertNotNull(fetchedQuest);

        assert(fetchedQuest.getId().equals(questId));
        assert(fetchedQuest.getTimeToCompleteInSeconds() == 3600);
        assert(fetchedQuest.getRewardsByCompletedOrders().equals(Map.of(1, 100, 2, 200, 3, 300)));
    }

    @Test
    void updateQuest() {

        Quest quest = new Quest(3600, Map.of(1, 100, 2, 200, 3, 300));

        ResponseEntity<Quest> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/quest",
            quest, Quest.class);

        assert(response.getStatusCode().is2xxSuccessful());

        Quest createdQuest = response.getBody();
        assertNotNull(createdQuest);

        Long questId = createdQuest.getId();

        Quest updatedQuest = new Quest(7200, Map.of(1, 200, 2, 400, 3, 600));

        HttpEntity<Quest> requestEntity = new HttpEntity<>(updatedQuest);

        ResponseEntity<Quest> updateResponse = this.restTemplate.exchange("http://localhost:" + port + "/api/v1/quest/" + questId,
            HttpMethod.PUT, requestEntity, Quest.class);

        assert(updateResponse.getStatusCode().is2xxSuccessful());

        Quest updatedQuestResponse = updateResponse.getBody();

        assertNotNull(updatedQuestResponse);

        assert(updatedQuestResponse.getId().equals(questId));
        assert(updatedQuestResponse.getTimeToCompleteInSeconds() == 7200);
        assert(updatedQuestResponse.getRewardsByCompletedOrders().equals(Map.of(1, 200, 2, 400, 3, 600)));
    }
}
