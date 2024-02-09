package com.example.harbourquests.data.entities;

import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "quests")
public class Quest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int timeToCompleteInSeconds;
    @ElementCollection
    @CollectionTable(name = "rewards_by_completed_orders",
                    joinColumns = @JoinColumn(name = "reward_id"))
    @MapKeyColumn(name = "completed_orders")
    @Column(name = "reward")
    private Map<Integer, Integer> rewardsByCompletedOrders;
}
