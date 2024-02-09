package com.example.harbourquests.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.harbourquests.data.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
