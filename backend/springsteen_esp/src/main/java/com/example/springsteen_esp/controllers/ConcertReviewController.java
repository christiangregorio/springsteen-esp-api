package com.example.springsteen_esp.controllers;

import com.example.springsteen_esp.models.ConcertReview;
import com.example.springsteen_esp.services.ConcertReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/concert-reviews")
@Tag(name = "Reviews de Conciertos", description = "Gestión de comentarios y reviews de conciertos")
public class ConcertReviewController {

    private final ConcertReviewService service;

    public ConcertReviewController(ConcertReviewService service) {
        this.service = service;
    }

    @Operation(
            summary = "Obtener review de un concierto",
            description = "Devuelve el review/comentario de un concierto específico si existe.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Review encontrado"),
                    @ApiResponse(responseCode = "404", description = "No hay review para este concierto")
            }
    )
    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertReview> getReview(@PathVariable Long concertId) {
        Optional<ConcertReview> review = service.getReviewByConcertId(concertId);
        return review.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Guardar o actualizar review",
            description = "Crea un nuevo review o actualiza uno existente para un concierto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Review guardado correctamente")
            }
    )
    @PostMapping("/{concertId}")
    public ResponseEntity<ConcertReview> saveReview(
            @PathVariable Long concertId,
            @RequestBody Map<String, String> payload) {

        String reviewText = payload.get("review");

        if (reviewText == null || reviewText.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ConcertReview saved = service.saveOrUpdateReview(concertId, reviewText);
        return ResponseEntity.ok(saved);
    }

    @Operation(
            summary = "Eliminar review de un concierto",
            description = "Elimina el review de un concierto específico.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Review eliminado correctamente")
            }
    )
    @DeleteMapping("/{concertId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long concertId) {
        service.deleteReview(concertId);
        return ResponseEntity.noContent().build();
    }
}