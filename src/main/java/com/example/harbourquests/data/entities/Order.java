package com.example.harbourquests.data.entities;

import com.example.harbourquests.enums.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String foodPickupAddress;
    private String deliverAddress;
    private String creationDate;
    private String deliveryDate;
    private OrderStatus status;
}
