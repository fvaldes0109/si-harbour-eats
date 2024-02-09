package com.example.harbourquests.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.harbourquests.data.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
