package com.usuarios.Demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.usuarios.Demo.model.UserModel;
import com.usuarios.Demo.service.UserModelService;
import com.usuarios.Demo.dto.ApiResponse;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/users")
public class UserModelController {

    private final UserModelService userModelService;

    public UserModelController(UserModelService userModelService) {
        this.userModelService = userModelService;
    }

/*Buscamos toods los usuairos */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserModel>>> getAllUsers() {
        try {
            List<UserModel> users = userModelService.getAllUsers();
            return ResponseEntity.ok(
                new ApiResponse<>("OK", "Usuarios obtenidos correctamente", users)
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>("ERROR", e.getMessage(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>("ERROR", "Error inesperado al obtener los usuarios", null)
            );
        }
    }

/*Buscmaos al usuario por id */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserModel>> getUserById(@PathVariable UUID id) {
        UserModel user = userModelService.getUserId(id);
        if (user != null) {
            return ResponseEntity.ok(
                new ApiResponse<>("OK", "Usuario encontrado", user)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>("ERROR", "Usuario con ID " + id + " no encontrado", null)
            );
        }
    }

/*Creamos un usuario */
    @PostMapping
    public ResponseEntity<ApiResponse<UserModel>> createUser(@RequestBody UserModel user) {
        try {
            UserModel nuevoUser = userModelService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("OK", "Usuario creado correctamente", nuevoUser)
            );
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiResponse<>("ERROR", e.getMessage(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>("ERROR", "Error inesperado al crear el usuario", null)
            );
        }
    }

/*Actualizamos al user usando su id */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserModel>> actualizarUser(@PathVariable UUID id, @RequestBody UserModel user) {
        try {
            UserModel updatedUser = userModelService.actualizarUser(id, user);
            return ResponseEntity.ok(
                new ApiResponse<>("OK", "Usuario actualizado correctamente", updatedUser)
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>("ERROR", e.getMessage(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>("ERROR", "Error inesperado al actualizar el usuario", null)
            );
        }
    }

/*Borramos usuarios */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable UUID id) {
        try {
            String mensaje = userModelService.matarUser(id);
            return ResponseEntity.ok(
                new ApiResponse<>("OK", mensaje, null)
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>("ERROR", e.getMessage(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>("ERROR", "Error inesperado al eliminar el usuario", null)
            );
        }
    }
}

