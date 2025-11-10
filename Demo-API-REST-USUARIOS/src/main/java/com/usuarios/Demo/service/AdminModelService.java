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
    List<AdminModel> admins = adminModelRepository.findAll();
    if (admins.isEmpty()) {
        throw new EntityNotFoundException("No existen administradores registrados en el sistema.");
    }
    return admins;
}

    public AdminModel getAdminId(UUID id) {
    Optional<AdminModel> admin = adminModelRepository.findById(id);
    if (admin.isEmpty()) {
        System.out.println("No existe el admin con ID " + id);
        return null;
    }
    return admin.get();
}


    public AdminModel CreateAdmin(AdminModel admin){
        Optional<AdminModel> existeAdmin = adminModelRepository.findById(admin.getId());
        if (existeAdmin.isPresent()) {
            throw new EntityExistsException("El admin con el ID " + admin.getId() + " ya existe :)");
        }
        return adminModelRepository.save(admin);
    }

    public AdminModel ActualizarAdmin(AdminModel admin){
        Optional<AdminModel> existeAdmin = adminModelRepository.findById(admin.getId());
        if (existeAdmin.isEmpty()){
            throw new EntityNotFoundException("El admin con ID " + admin.getId() + " no existe :(");
        }

        AdminModel adminActual = existeAdmin.get();

        if(admin.getPassword() !=null && !admin.getEmail().isEmpty()){
            adminActual.setEmail(admin.getPassword());
        }

        if(admin.getEmail() != null && !admin.getEmail().isEmpty()){
            adminActual.setEmail((admin.getEmail()));
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

    public void matarAdmin(UUID id){
        adminModelRepository.deleteById(id);
    }
}

//Poner validaciones aquí!