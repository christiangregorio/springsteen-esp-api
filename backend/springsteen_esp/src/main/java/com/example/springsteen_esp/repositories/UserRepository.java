package com.example.springsteen_esp.repositories;

import com.example.springsteen_esp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Podremos hacer consultas personalizadas si es necesario
    User findByUsername(String username);
    User findByEmail(String email);
}