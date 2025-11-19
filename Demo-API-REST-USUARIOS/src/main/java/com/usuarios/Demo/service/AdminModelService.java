package com.usuarios.Demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.usuarios.Demo.model.AdminModel;
import com.usuarios.Demo.repository.IAdminModelRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional //Toma todo como una sola transacción. Si una no funciona, no se entrega nada.
public class AdminModelService {

    private final IAdminModelRepository adminModelRepository;

    public AdminModelService(IAdminModelRepository adminModelRepository){
        this.adminModelRepository = adminModelRepository;
    }

    public List<AdminModel> getAllAdmins() {
        List<AdminModel> admin = adminModelRepository.findAll();
        if (admin.isEmpty()) {
        throw new EntityNotFoundException("No existen administradores registrados en el sistema.");
        }
    return admin;
    }

    public AdminModel getAdminId(UUID id) {
        return adminModelRepository.findById(id).orElse(null);
    }


    public AdminModel CreateAdmin(AdminModel admin){
        Optional<AdminModel> existeAdmin = adminModelRepository.findById(admin.getId());
        if (existeAdmin.isPresent()) {
            throw new EntityExistsException("El admin con el ID " + admin.getId() + " ya existe :)");
        }
        return adminModelRepository.save(admin);
    }

    public AdminModel ActualizarAdmin(UUID id,AdminModel admin){
        Optional<AdminModel> existeAdmin = adminModelRepository.findById(id);
        if (existeAdmin.isEmpty()){
            throw new EntityNotFoundException("El admin con ID " + admin.getId() + " no existe :(");
        }

        AdminModel adminActual = existeAdmin.get();

        if(admin.getPassword() !=null && !admin.getPassword().isEmpty()){
            adminActual.setEmail(admin.getPassword());
        }

        if(admin.getEmail() != null && !admin.getEmail().isEmpty()){
            adminActual.setEmail(admin.getEmail());
        }

        if(admin.getAddress() != null && admin.getAddress().isEmpty()){
            adminActual.setAddress(admin.getAddress());
        }

        if(admin.getAvatarURL() != null && !admin.getAvatarURL().isEmpty()){
            adminActual.setAvatarURL(admin.getAvatarURL());
        }
        
        return adminModelRepository.save(adminActual);
    }
    /*Usamos optional para evitar errores de tipo nullPinterException(cuadno intentamos
      entrar en un objeto que es null) */

    public String matarAdmin(UUID id){
        Optional<AdminModel> existeAdmin = adminModelRepository.findById(id);
        if(existeAdmin.isEmpty()){
            throw new EntityNotFoundException("No se ha encontrado al administrador de ID " +id );
        }
        AdminModel admin = existeAdmin.get();
        adminModelRepository.deleteById(id);

        return "Se ha eliminado al admimistrador: " + admin.getUsername() + " " + admin.getLastname() + ", de ID: " + admin.getId();

    }
}

