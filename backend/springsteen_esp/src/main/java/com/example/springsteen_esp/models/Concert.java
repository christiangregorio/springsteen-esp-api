package com.example.springsteen_esp.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "concerts")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String city;
    private String venue;
    private String tour;
    private String duration;

    public Concert() {}

    public Concert(LocalDate date, String city, String venue, String tour, String duration) {
        this.date = date;
        this.city = city;
        this.venue = venue;
        this.tour = tour;
        this.duration = duration;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getTour() { return tour; }
    public void setTour(String tour) { this.tour = tour; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
}