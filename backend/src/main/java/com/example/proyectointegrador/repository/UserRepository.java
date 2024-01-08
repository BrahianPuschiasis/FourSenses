package com.example.proyectointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyectointegrador.entity.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findById(Long userId);
}