package com.example.springsteen_esp.services;

import com.example.springsteen_esp.models.ConcertReview;
import com.example.springsteen_esp.repositories.ConcertReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ConcertReviewService {

    private final ConcertReviewRepository repository;

    public ConcertReviewService(ConcertReviewRepository repository) {
        this.repository = repository;
    }

    // Obtener la review del concierto mediante su ID
    public Optional<ConcertReview> getReviewByConcertId(Long concertId) {
        return repository.findByConcertId(concertId);
    }

    // Crear o actualizar review
    public ConcertReview saveOrUpdateReview(Long concertId, String reviewText) {
        Optional<ConcertReview> existing = repository.findByConcertId(concertId);

        if (existing.isPresent()) {
            // Actualizar review existente
            ConcertReview review = existing.get();
            review.setReview(reviewText);
            return repository.save(review);
        } else {
            // Crear una nueva review
            ConcertReview newReview = new ConcertReview(concertId, reviewText);
            return repository.save(newReview);
        }
    }

    // Eliminar review
    @Transactional
    public void deleteReview(Long concertId) {
        repository.deleteByConcertId(concertId);
    }
}