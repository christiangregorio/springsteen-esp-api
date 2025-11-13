package com.example.springsteen_esp.repositories;

import com.example.springsteen_esp.models.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {

    List<Concert> findAllByOrderByDateDesc();
}
