package com.usuarios.Demo.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.usuarios.Demo.dto.APIResponse;
import com.usuarios.Demo.model.AdminModel;
import com.usuarios.Demo.service.AdminModelService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminModelController {

    private final AdminModelService adminModelService;
    public AdminModelController(AdminModelService adminModelService) {
        this.adminModelService = adminModelService;
    }

/*Buscamos a todos los administradores que hayan */
    @GetMapping
    public ResponseEntity<?> getAllAdmins() {
        try {
            List<AdminModel> admins = adminModelService.getAllAdmins();
            return ResponseEntity.ok(
                new APIResponse<>("OK", "Lista de administradores obtenida correctamente", admins)
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new APIResponse<>("ERROR", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new APIResponse<>("ERROR", "Error inesperado al obtener administradores", null));
        }
    }

/*Buscamos un administrador por su id */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminId(@PathVariable UUID id) {
        try {
            AdminModel admin = adminModelService.getAdminId(id);
            if (admin == null) {
                throw new EntityNotFoundException("Administrador no encontrado con ID " + id);
            }
            return ResponseEntity.ok(
                new APIResponse<>("OK", "Administrador encontrado", admin)
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new APIResponse<>("ERROR", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new APIResponse<>("ERROR", "Error inesperado al buscar el administrador", null));
        }
    }

/*Creamos un administrador */
    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody AdminModel admin) {
        try {
            AdminModel nuevoAdmin = adminModelService.CreateAdmin(admin);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>("OK", "Administrador creado correctamente", nuevoAdmin));
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new APIResponse<>("ERROR", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new APIResponse<>("ERROR", "Error inesperado al crear el administrador", null));
        }
    }

/*Actualizamos al administrador */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAdmin(@PathVariable UUID id, @RequestBody AdminModel admin) {
        try {
            AdminModel actualizado = adminModelService.ActualizarAdmin(id, admin);
            return ResponseEntity.ok(
                new APIResponse<>("OK", "Administrador actualizado correctamente", actualizado)
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new APIResponse<>("ERROR", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new APIResponse<>("ERROR", "Error inesperado al actualizar el administrador", null));
        }
    }

/*Eliminamos al administrador a travez de su id */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> matarAdmin(@PathVariable UUID id) {
        try {
            String mensaje = adminModelService.matarAdmin(id);
            return ResponseEntity.ok(
                new APIResponse<>("OK", mensaje, null)
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new APIResponse<>("ERROR", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new APIResponse<>("ERROR", "Error inesperado al eliminar el administrador", null));
        }
    }
}

