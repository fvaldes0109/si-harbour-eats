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

    @SuppressWarnings("null")
    public Quest createQuest(Quest entity) {

        return questRepository.save(entity);
    }

    @SuppressWarnings("null")
    public Quest updateQuest(Long questId, Quest entity) {

        Quest quest = questRepository.findById(questId).get();
        quest.setTimeToCompleteInSeconds(entity.getTimeToCompleteInSeconds());
        quest.setRewardsByCompletedOrders(entity.getRewardsByCompletedOrders());
        return questRepository.save(quest);
    }

    @SuppressWarnings("null")
    public Quest getQuestById(Long questId) {

        return questRepository.findById(questId).get();
    }
}
