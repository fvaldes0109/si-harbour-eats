package com.example.harbourquests.data.entities;

import com.example.harbourquests.enums.QuestCourierStatus;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "quest_couriers")
public class QuestCourier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Quest quest;
    @ManyToOne
    private User user;
    private QuestCourierStatus status;
    private Integer ordersDelivered;

    public QuestCourier(User user, Quest quest) {

        this.quest = quest;
        this.user = user;
        this.status = QuestCourierStatus.inProgress;
        this.ordersDelivered = 0;
    }
}
