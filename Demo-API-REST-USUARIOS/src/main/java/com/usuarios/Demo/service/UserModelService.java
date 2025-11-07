package com.usuarios.Demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.usuarios.Demo.model.UserModel;
import com.usuarios.Demo.repository.IUserModelRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserModelService {
    
    private final IUserModelRepository userModelRepository;

    public UserModelService(IUserModelRepository userModelRepository){
        this.userModelRepository = userModelRepository;
    }

    public List<UserModel> getAllUsers(){
        return userModelRepository.findAll();
    }

    public UserModel getUserId(UUID id){
        return userModelRepository.findById(id).orElse(null);
    }

    public UserModel CreateUser(UserModel user){
        return userModelRepository.save(user);
    }

    public UserModel ActualziarUser(UserModel user){
        return userModelRepository.save(user);
    }

    public void matarUser(UUID id){
        userModelRepository.deleteById(id);
    }

    //AHORA RECUERDA PONERLE LAS VALIDACIONES!
}


