package com.example.Catalog.Repositories;

import com.example.Catalog.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByLogin(String login);
}