package com.example.springsteen_esp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_concert_attendance")
public class UserConcertAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID autogenerado para cada fila

    @ManyToOne // Relación 1:N con User
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne // Relación 1:N con Concert
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    public UserConcertAttendance() {}

    public UserConcertAttendance(User user, Concert concert) {
        this.user = user;
        this.concert = concert;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Concert getConcert() { return concert; }
    public void setConcert(Concert concert) { this.concert = concert; }
}