package com.usuarios.Demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.usuarios.Demo.model.AdminModel;
import com.usuarios.Demo.repository.IAdminModelRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional //Toma todo como una sola transacción. Si una no funciona, no se entrega nada.
public class AdminModelService {

    private final IAdminModelRepository adminModelRepository;

    public AdminModelService(IAdminModelRepository adminModelRepository){
        this.adminModelRepository = adminModelRepository;
    }

    public List<AdminModel> getAllAdmins(){
        return adminModelRepository.findAll();
    }
}
