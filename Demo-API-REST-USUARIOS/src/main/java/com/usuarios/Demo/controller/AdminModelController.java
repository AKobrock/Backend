package com.usuarios.Demo.controller;

import java.util.List;
import java.util.UUID;

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

@RestController
@RequestMapping("/api/v1/admins")
public class AdminModelController {

    private final AdminModelService adminModelService;
    public AdminModelController(AdminModelService adminModelService){
        this.adminModelService = adminModelService;
    }

    @GetMapping("/")
    public List<AdminModel> getAllAdmins(){
        return adminModelService.getAllAdmins();
    }

    @GetMapping("/admins/{id}")
    public AdminModel getAdminId(@RequestParam UUID id){
        return adminModelService.getAdminId(id);
    }

    @PostMapping("/admins")
    public AdminModel createAdmin(@RequestBody AdminModel admin){
        return adminModelService.CreateAdmin(admin);
    }

    @PutMapping("/admins/{id}")
    public AdminModel actualizarAdmin(@PathVariable UUID id, @RequestBody AdminModel admin){
        AdminModel existingAdminModel = adminModelService.getAdminId(id);
        if(existingAdminModel != null){
            existingAdminModel.setPassword(admin.getPassword());
        }
        return existingAdminModel;
    }

}
