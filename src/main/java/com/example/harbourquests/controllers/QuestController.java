package com.example.harbourquests.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.harbourquests.data.entities.Quest;
import com.example.harbourquests.data.repositories.QuestRepository;

@RestController
public class QuestController {
    
    @Autowired
    private QuestRepository questRepository;

    public Iterable<Quest> getQuests() {
        return questRepository.findAll();
    }

    public Quest createQuest(Quest entity) {

        if (entity == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quest is required");

        return questRepository.save(entity);
    }

    public Quest updateQuest(Long questId, Quest entity) {

        if (questId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quest ID is required");

        Quest quest = questRepository.findById(questId).get();
        quest.setTimeToCompleteInSeconds(entity.getTimeToCompleteInSeconds());
        quest.setRewardsByCompletedOrders(entity.getRewardsByCompletedOrders());
        return questRepository.save(quest);
    }

    public Quest getQuestById(Long questId) {

        if (questId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quest ID is required");

        return questRepository.findById(questId).get();
    }
}
