package com.example.springsteen_esp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Entidad que representa a un usuario del sistema. " +
                "Cada usuario puede asistir a varios conciertos registrados en la base de datos."
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único del usuario (autogenerado por la base de datos).",
            example = "1"
    )
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    @Schema(
            description = "Nombre de usuario único en el sistema.",
            example = "springsteenFan"
    )
    private String username;

    @Column(length = 100, nullable = false, unique = true)
    @Schema(
            description = "Correo electrónico único del usuario.",
            example = "fan@example.com"
    )
    private String email;

    @Column(length = 100, nullable = false)
    @Schema(
            description = "Contraseña del usuario (en texto plano solo para pruebas, debe encriptarse en producción).",
            example = "1234secure"
    )
    private String password;

    // Relación N:M de usuarios que asisten a conciertos.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // evita recursión infinita en toString()
    @EqualsAndHashCode.Exclude
    @JsonManagedReference("user-attendance") // ✅ evita recursión circular en JSON
    @Schema(
            description = "Lista de asistencias del usuario a conciertos registrados."
    )
    private List<UserConcertAttendance> attendances = new ArrayList<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}