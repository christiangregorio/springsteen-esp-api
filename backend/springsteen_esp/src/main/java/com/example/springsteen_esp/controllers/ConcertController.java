package com.example.springsteen_esp.controllers;

import com.example.springsteen_esp.models.Concert;
import com.example.springsteen_esp.models.Song;
import com.example.springsteen_esp.models.User;
import com.example.springsteen_esp.services.ConcertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/concerts")
@Tag(name = "Conciertos", description = "Operaciones relacionadas con la gestión de conciertos, canciones y asistentes")
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @Operation(
            summary = "Obtener todos los conciertos",
            description = "Devuelve una lista completa con todos los conciertos registrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de conciertos obtenida correctamente")
            }
    )

    @GetMapping
    public ResponseEntity<List<Concert>> getAllConcerts() {
        List<Concert> concerts = concertService.list();
        return ResponseEntity.ok(concerts);
    }

    @Operation(
            summary = "Obtener concierto por ID",
            description = "Busca un concierto específico por su identificador único.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Concierto encontrado"),
                    @ApiResponse(responseCode = "404", description = "Concierto no encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Concert> getConcertById(@PathVariable Long id) {
        Concert concert = concertService.get(id);
        if (concert == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(concert);
    }

    @Operation(
            summary = "Crear un nuevo concierto",
            description = "Registra un nuevo concierto en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Concierto creado correctamente")
            }
    )
    @PostMapping
    public ResponseEntity<Concert> createConcert(@RequestBody Concert concert) {
        Concert created = concertService.save(concert);
        return ResponseEntity.ok(created);
    }

    @Operation(
            summary = "Actualizar un concierto existente",
            description = "Modifica los datos de un concierto ya existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Concierto actualizado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Concierto no encontrado")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Concert> updateConcert(@PathVariable Long id, @RequestBody Concert updatedConcert) {
        Concert existing = concertService.get(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setDate(updatedConcert.getDate());
        existing.setCity(updatedConcert.getCity());
        existing.setVenue(updatedConcert.getVenue());
        existing.setTour(updatedConcert.getTour());
        existing.setDuration(updatedConcert.getDuration());

        //ACTUALIZAR CANCIONES
        //Borrar canciones antiguas
        existing.getSongs().clear();

        //Añadir canciones nuevas
        if (updatedConcert.getSongs() != null) {
            for (Song song : updatedConcert.getSongs()) {
                song.setConcert(existing);  // Vincular la canción al concierto
                existing.getSongs().add(song);
            }
        }

        Concert saved = concertService.save(existing);
        return ResponseEntity.ok(saved);
    }

    @Operation(
            summary = "Eliminar un concierto",
            description = "Elimina un concierto del sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Concierto eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Concierto no encontrado")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcert(@PathVariable Long id) {
        Concert concert = concertService.get(id);
        if (concert == null) {
            return ResponseEntity.notFound().build();
        }
        concertService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener canciones de un concierto",
            description = "Devuelve las canciones interpretadas en un concierto, ordenadas por su posición.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de canciones obtenida correctamente"),
                    @ApiResponse(responseCode = "204", description = "No hay canciones registradas para este concierto")
            }
    )
    @GetMapping("/{id}/songs")
    public ResponseEntity<List<Song>> getSongsOfConcert(@PathVariable Long id) {
        List<Song> songs = concertService.getSongsOrdered(id);
        if (songs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(songs);
    }

    @Operation(
            summary = "Añadir una canción a un concierto",
            description = "Agrega una nueva canción al setlist del concierto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Canción añadida correctamente"),
                    @ApiResponse(responseCode = "404", description = "Concierto no encontrado")
            }
    )
    @PostMapping("/{id}/songs")
    public ResponseEntity<Concert> addSongToConcert(@PathVariable Long id, @RequestBody Song song) {
        Concert updatedConcert = concertService.addSong(id, song);
        if (updatedConcert == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedConcert);
    }

    @Operation(
            summary = "Eliminar una canción de un concierto",
            description = "Quita una canción del setlist de un concierto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Canción eliminada correctamente"),
                    @ApiResponse(responseCode = "404", description = "Concierto no encontrado")
            }
    )
    @DeleteMapping("/{id}/songs/{songId}")
    public ResponseEntity<Concert> removeSongFromConcert(@PathVariable Long id, @PathVariable Long songId) {
        Concert updatedConcert = concertService.removeSongById(id, songId);
        if (updatedConcert == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedConcert);
    }

    @Operation(
            summary = "Obtener asistentes de un concierto",
            description = "Devuelve la lista de usuarios que asistieron a un concierto determinado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de asistentes obtenida correctamente"),
                    @ApiResponse(responseCode = "204", description = "No hay asistentes registrados para este concierto")
            }
    )
    @GetMapping("/{id}/attendees")
    public ResponseEntity<List<User>> getAttendeesOfConcert(@PathVariable Long id) {
        List<User> attendees = concertService.getAttendees(id);
        if (attendees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(attendees);
    }

    @Operation(
            summary = "Contar asistentes de un concierto",
            description = "Devuelve el número total de asistentes registrados para un concierto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cantidad de asistentes obtenida correctamente")
            }
    )
    @GetMapping("/{id}/attendees/count")
    public ResponseEntity<Integer> countAttendees(@PathVariable Long id) {
        int count = concertService.countAttendees(id);
        return ResponseEntity.ok(count);
    }
}