package com.example.springsteen_esp.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "concerts")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String venue;

    @Column(length = 100)
    private String tour;

    @Column(length = 20)
    private String duration;

    // Relación N:M de concierto y usuarios que asisten.
    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserConcertAttendance> attendances = new ArrayList<>();

    // Relación 1:N con canciones (setlist)
    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();

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

    public List<UserConcertAttendance> getAttendances() { return attendances; }
    public void setAttendances(List<UserConcertAttendance> attendances) { this.attendances = attendances; }

    public List<Song> getSongs() { return songs; }
    public void setSongs(List<Song> songs) { this.songs = songs; }
}