package com.example.harbourquests.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
        return questRepository.save(entity);
    }

    public Quest updateQuest(Long id, Quest entity) {

        Quest quest = questRepository.findById(id).get();
        quest.setTimeToCompleteInSeconds(entity.getTimeToCompleteInSeconds());
        return questRepository.save(quest);
    }

    public Quest getQuestById(Long id) {
        return questRepository.findById(id).get();
    }
}
