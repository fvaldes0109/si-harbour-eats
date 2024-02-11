package com.example.harbourquests.data.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(name = "users",
       uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    @OneToMany(mappedBy = "user")
    private List<QuestCourier> questCouriers;

    public User() {
        
    }

    public User(String username) {
        this.username = username;
    }
}
