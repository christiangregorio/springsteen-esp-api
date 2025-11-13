package com.example.springsteen_esp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_concert_attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa la asistencia de un usuario a un concierto específico.")
public class UserConcertAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del registro de asistencia.", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-attendance") // Evita bucle con User
    @Schema(description = "Usuario que asistió al concierto.")
    private User user;

    @ManyToOne
    @JoinColumn(name = "concert_id", nullable = false)
    @JsonBackReference("concert-attendance") // Evita bucle con Concert
    @Schema(description = "Concierto al que asistió el usuario.")
    private Concert concert;

    public UserConcertAttendance(User user, Concert concert) {
        this.user = user;
        this.concert = concert;
    }
}
