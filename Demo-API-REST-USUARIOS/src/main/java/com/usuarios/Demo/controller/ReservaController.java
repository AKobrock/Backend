package com.usuarios.Demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.usuarios.Demo.model.ReservaModel;
import com.usuarios.Demo.service.ReservaService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /* Obtener todas las reservas */
    @GetMapping
    public ResponseEntity<?> obtenerReservas() {
        try {
            List<ReservaModel> reservas = reservaService.getAllReservas();
            return ResponseEntity.ok(reservas);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /* Obtener una reserva por ID */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerReservaPorId(@PathVariable UUID id) {
        try {
            ReservaModel reserva = reservaService.getReservaById(id);
            return ResponseEntity.ok(reserva);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /* Crear nueva reserva */
    @PostMapping
    public ResponseEntity<?> crearReserva(
            @RequestParam UUID userId,
            @RequestParam UUID papaId,
            @RequestParam String fechaVisita,
            @RequestParam String direccion) {

        try {
            LocalDate fecha = LocalDate.parse(fechaVisita);
            ReservaModel nueva = reservaService.crearReserva(userId, papaId, fecha, direccion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la reserva: " + e.getMessage());
        }
    }

    /* Cancelar una reserva */
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable UUID id) {
        try {
            ReservaModel cancelada = reservaService.cancelarReserva(id);
            return ResponseEntity.ok(cancelada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /* Eliminar reserva */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable UUID id) {
        try {
            String mensaje = reservaService.eliminarReserva(id);
            return ResponseEntity.ok(mensaje);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
