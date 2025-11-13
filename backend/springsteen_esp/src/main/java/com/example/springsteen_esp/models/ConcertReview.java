package com.example.springsteen_esp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "concert_reviews")
public class ConcertReview {

    @Id
    private String id;

    private Long concertId;  // ID del concierto en mysql
    private String review;    // Texto del review/comentario

    public ConcertReview() {}

    public ConcertReview(Long concertId, String review) {
        this.concertId = concertId;
        this.review = review;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}