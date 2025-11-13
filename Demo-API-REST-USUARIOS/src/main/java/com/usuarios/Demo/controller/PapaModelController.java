package com.usuarios.Demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.usuarios.Demo.model.PapaModel;
import com.usuarios.Demo.service.PapaModelService;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/papas")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier frontend
public class PapaModelController {

    private final PapaModelService papaModelService;

    public PapaModelController(PapaModelService papaModelService) {
        this.papaModelService = papaModelService;
    }

    /* Obtener todos los papás */
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosPapas() {
        try {
            List<PapaModel> papas = papaModelService.getAllPapas();
            return ResponseEntity.ok(papas);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /* Obtener papá por ID */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPapaPorId(@PathVariable UUID id) {
        try {
            PapaModel papa = papaModelService.getPapaById(id);
            return ResponseEntity.ok(papa);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /* Crear un nuevo papá */
    @PostMapping
    public ResponseEntity<?> crearPapa(@RequestBody PapaModel papa) {
        try {
            PapaModel nuevoPapa = papaModelService.createPapa(papa);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPapa);
        } catch (IllegalArgumentException | EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el papá: " + e.getMessage());
        }
    }

    /* Actualizar papá existente */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPapa(@PathVariable UUID id, @RequestBody PapaModel papa) {
        try {
            PapaModel papaActualizado = papaModelService.actualizarPapa(id, papa);
            return ResponseEntity.ok(papaActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el papá: " + e.getMessage());
        }
    }

    /* Eliminar papá (se fue a comprar cigarros 😢) */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPapa(@PathVariable UUID id) {
        try {
            String mensaje = papaModelService.papaFueAComprarCigarros(id);
            return ResponseEntity.ok(mensaje);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el papá: " + e.getMessage());
        }
    }
}

