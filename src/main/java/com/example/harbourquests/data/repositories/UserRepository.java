package com.example.harbourquests.data.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.harbourquests.data.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);
}
