package com.example.harbourquests.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.harbourquests.data.entities.QuestCourier;
import com.example.harbourquests.data.repositories.QuestCourierRepository;
import com.example.harbourquests.data.repositories.QuestRepository;
import com.example.harbourquests.data.repositories.UserRepository;
import com.example.harbourquests.enums.QuestCourierStatus;

@RestController
public class CourierController {
    
    @Autowired
    private QuestCourierRepository questCourierRepository;
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private UserRepository userRepository;

    public QuestCourier startQuestCourier(String username, Long questId) {

        var user = userRepository.findByUsername(username);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        var quest = questRepository.findById(questId);
        if (quest.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quest not found");
        
        if (questCourierRepository.numberOfActiveQuest(username) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has an active quest");
        }

        var questCourier = new QuestCourier(user, quest.get());
        return questCourierRepository.save(questCourier);
    }

    public Iterable<QuestCourier> getHistory(String username) {
        
        return questCourierRepository.findByUserAndNotStatus(username, QuestCourierStatus.completed);
    }
}
