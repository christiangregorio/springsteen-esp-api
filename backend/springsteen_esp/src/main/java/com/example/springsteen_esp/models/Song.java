package com.example.springsteen_esp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "songs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una canción interpretada en un concierto de Bruce Springsteen.")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la canción.", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Posición de la canción en el setlist del concierto.", example = "3")
    private int position;

    @Column(length = 200, nullable = false)
    @Schema(description = "Título de la canción.", example = "Born to Run")
    private String title;

    // Relación 1:N con Concert
    @ManyToOne
    @JoinColumn(name = "concert_id", nullable = false)
    @JsonBackReference("concert-song") // Solucion para recursión infinita con Concert
    @Schema(description = "Concierto al que pertenece esta canción.")
    private Concert concert;
}