package com.example.springsteen_esp.repositories;

import com.example.springsteen_esp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuario por username
    User findByUsername(String username);

    // Buscar usuario por email
    User findByEmail(String email);
}