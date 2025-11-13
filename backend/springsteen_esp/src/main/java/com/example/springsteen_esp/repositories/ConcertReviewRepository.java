package com.example.springsteen_esp.repositories;

import com.example.springsteen_esp.models.ConcertReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConcertReviewRepository extends MongoRepository<ConcertReview, String> {

    // Buscar la review por ID de concierto
    Optional<ConcertReview> findByConcertId(Long concertId);

    // Eliminar la review por ID de concierto
    void deleteByConcertId(Long concertId);
}