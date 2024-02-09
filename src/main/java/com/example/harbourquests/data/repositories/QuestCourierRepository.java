package com.example.harbourquests.data.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.harbourquests.data.entities.QuestCourier;

public interface QuestCourierRepository extends CrudRepository<QuestCourier, Long>{

    // TODO: Change that hardcoded status
    @Query("SELECT COUNT(*) FROM QuestCourier qc WHERE qc.user.username = ?1 AND qc.status = 2")
    Long numberOfActiveQuest(String username);
}