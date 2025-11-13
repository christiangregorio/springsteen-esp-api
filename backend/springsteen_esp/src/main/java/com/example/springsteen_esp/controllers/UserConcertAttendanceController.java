package com.example.springsteen_esp.controllers;

import com.example.springsteen_esp.models.Concert;
import com.example.springsteen_esp.models.User;
import com.example.springsteen_esp.models.UserConcertAttendance;
import com.example.springsteen_esp.services.ConcertService;
import com.example.springsteen_esp.services.UserService;
import com.example.springsteen_esp.services.UserConcertAttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/attendances")
@Tag(name = "Asistencias", description = "Gestión de asistencias de usuarios a conciertos")
public class UserConcertAttendanceController {

    private final UserConcertAttendanceService attendanceService;
    private final UserService userService;
    private final ConcertService concertService;

    public UserConcertAttendanceController(
            UserConcertAttendanceService attendanceService,
            UserService userService,
            ConcertService concertService
    ) {
        this.attendanceService = attendanceService;
        this.userService = userService;
        this.concertService = concertService;
    }

    @Operation(
            summary = "Marcar asistencia de un usuario a un concierto",
            description = "Registra que un usuario asistió a un concierto específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Asistencia registrada correctamente"),
                    @ApiResponse(responseCode = "400", description = "Usuario o concierto no encontrado")
            }
    )
    @PostMapping("/user/{userId}/concert/{concertId}")
    public ResponseEntity<?> markAttendance(@PathVariable Long userId, @PathVariable Long concertId) {
        User user = userService.get(userId);
        Concert concert = concertService.get(concertId);

        if (user == null || concert == null) {
            return ResponseEntity.badRequest().body("Usuario o concierto no encontrado");
        }

        UserConcertAttendance attendance = attendanceService.markAttendance(user, concert);
        return ResponseEntity.ok(attendance);
    }

    @Operation(
            summary = "Eliminar asistencia de un usuario a un concierto",
            description = "Elimina el registro de asistencia de un usuario a un concierto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Asistencia eliminada correctamente"),
                    @ApiResponse(responseCode = "400", description = "Usuario o concierto no encontrado")
            }
    )
    @DeleteMapping("/user/{userId}/concert/{concertId}")
    public ResponseEntity<String> removeAttendance(@PathVariable Long userId, @PathVariable Long concertId) {
        User user = userService.get(userId);
        Concert concert = concertService.get(concertId);

        if (user == null || concert == null) {
            return ResponseEntity.badRequest().body("Usuario o concierto no encontrado");
        }

        attendanceService.removeAttendance(user, concert);
        return ResponseEntity.ok("Asistencia eliminada correctamente");
    }
}