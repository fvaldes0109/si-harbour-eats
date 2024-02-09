package com.example.harbourquests.data.entities;

import java.util.List;

import com.example.harbourquests.enums.QuestCourierStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @JsonIgnoreProperties("questCouriers")
    private User user;
    private QuestCourierStatus status;
    @OneToMany(mappedBy = "questCourier", cascade = CascadeType.ALL)
    private List<Order> orders;

    public QuestCourier() {
    }

    public QuestCourier(User user, Quest quest) {

        this.quest = quest;
        this.user = user;
        this.status = QuestCourierStatus.inProgress;
    }

    public void addOrder(Order order) {

        this.orders.add(order);
        order.setQuestCourier(this);
    }
}
