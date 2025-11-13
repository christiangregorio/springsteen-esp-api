package com.example.springsteen_esp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "concerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Entidad que representa un concierto de Bruce Springsteen, incluyendo su fecha, ciudad, gira y setlist."
)
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del concierto.", example = "1")
    private Long id;

    @Schema(description = "Fecha del concierto.", example = "1981-04-21")
    private LocalDate date;

    @Column(length = 100)
    @Schema(description = "Ciudad donde tuvo lugar el concierto.", example = "Barcelona")
    private String city;

    @Column(length = 100)
    @Schema(description = "Nombre del recinto o estadio donde se celebró el concierto.", example = "Palacio Municipal de Deportes de Montjuïc")
    private String venue;

    @Column(length = 100)
    @Schema(description = "Nombre de la gira a la que pertenece el concierto.", example = "The River Tour")
    private String tour;

    @Column(length = 20)
    @Schema(description = "Duración aproximada del concierto.", example = "3h 10m")
    private String duration;

    // Relación N:M con usuarios (asistencias)
    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("concert-attendance") // evita recursión en bucle al devolver conciertos con sus asistencias
    @Schema(description = "Lista de asistencias registradas de usuarios que acudieron al concierto.")
    private List<UserConcertAttendance> attendances = new ArrayList<>();

    // Relación 1:N con canciones (setlist)
    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("concert-song") // evita la recursión en bucle al devolver conciertos con sus canciones
    @Schema(description = "Setlist de canciones interpretadas durante el concierto.")
    private List<Song> songs = new ArrayList<>();

    public Concert(LocalDate date, String city, String venue, String tour, String duration) {
        this.date = date;
        this.city = city;
        this.venue = venue;
        this.tour = tour;
        this.duration = duration;
    }
}