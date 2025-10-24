package com.example.springsteen_esp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int position; // Orden en el setlist
    private String title; // Título de la canción

    @ManyToOne
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    public Song() {}

    public Song(int position, String title, Concert concert) {
        this.position = position;
        this.title = title;
        this.concert = concert;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Concert getConcert() { return concert; }
    public void setConcert(Concert concert) { this.concert = concert; }
}