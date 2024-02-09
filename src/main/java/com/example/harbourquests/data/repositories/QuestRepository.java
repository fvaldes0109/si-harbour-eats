package com.example.harbourquests.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.harbourquests.data.entities.Quest;

public interface QuestRepository extends CrudRepository<Quest, Long>{

}
