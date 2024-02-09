package com.example.harbourquests.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// TODO: Add the rewards map

@Entity
@Data
@Table(name = "quests")
public class Quest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int timeToCompleteInSeconds;
}
