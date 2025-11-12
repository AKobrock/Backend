package com.usuarios.Demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.usuarios.Demo.model.AdminModel;
import com.usuarios.Demo.service.AdminModelService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminModelController {

    private final AdminModelService adminModelService;
    public AdminModelController(AdminModelService adminModelService){
        this.adminModelService = adminModelService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllAdmins(){
        try {
            List<AdminModel> admin = adminModelService.getAllAdmins();
            return ResponseEntity.ok(admin);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran administradores en el servidor.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Error inesperado al crear el admin.");
        }
    }

    @GetMapping("/admins/{id}")
    public ResponseEntity<?> getAdminId(@RequestParam UUID id){
        AdminModel existingAdminModel = adminModelService.getAdminId(id);
        if(existingAdminModel != null){
            return ResponseEntity.ok(existingAdminModel);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró administrador de id" + id);
    }

    @PostMapping("/admins")
    public ResponseEntity<?> createAdmin(@RequestBody AdminModel admin){
         try {
            AdminModel nuevoAdmin = adminModelService.CreateAdmin(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAdmin); // 201 Created
        } 
        catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT) // 409 Conflict
                    .body(" Error: " + e.getMessage());
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) //ERROR 400 
                    .body(" Error inesperado al crear el admin.");
        }
    }

    @PutMapping("/admins/{id}")
    public ResponseEntity<?> actualizarAdmin(@PathVariable UUID id, @RequestBody AdminModel admin){
        try {
            AdminModel existingAdminModel = adminModelService.ActualizarAdmin(id,admin);
            return ResponseEntity.status(HttpStatus.OK).body(existingAdminModel);
        } 
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("mensaje");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error inesperado al crear el admin.");
        }
        
    }

}
