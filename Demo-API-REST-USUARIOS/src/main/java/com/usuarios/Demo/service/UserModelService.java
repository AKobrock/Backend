package com.usuarios.Demo.service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.usuarios.Demo.model.UserModel;
import com.usuarios.Demo.repository.IUserModelRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserModelService {
    
    private final IUserModelRepository userModelRepository;

    public UserModelService(IUserModelRepository userModelRepository){
        this.userModelRepository = userModelRepository;
    }

    public List<UserModel> getAllUsers(){
        List<UserModel> user = userModelRepository.findAll();
        if(user.isEmpty()){
            throw new EntityNotFoundException("No existen usuarios registrados");
        }
        return user;
    }

    public UserModel getUserId(UUID id){
        return userModelRepository.findById(id).orElse(null);
    }

    public UserModel createUser(UserModel user){
        Optional<UserModel> existeUser = userModelRepository.findById(user.getId());
        if (existeUser.isPresent()){
            throw new EntityExistsException("El usuario con ID " + user.getId() + " ya existe." );
        }
        return userModelRepository.save(user);
    }

    public UserModel actualizarUser(UUID id, UserModel user){
        Optional<UserModel> existeUser = userModelRepository.findById(id);
        if (existeUser.isEmpty()){
            throw new EntityNotFoundException("El usuario con ID " + user.getId() + " no existe.");
        }

        UserModel userActual = existeUser.get();

        if(user.getPassword() !=null && !user.getPassword().isEmpty()){
            userActual.setEmail(user.getPassword());
        }

        if(user.getEmail() !=null && !user.getEmail().isEmpty()){
            userActual.setEmail(user.getEmail());
        }

        if(user.getAddress() !=null && !user.getAddress().isEmpty()){
            userActual.setAddress(user.getAddress());
        }

        if(user.getAvatarURL() !=null && !user.getAvatarURL().isEmpty()){
            userActual.setAvatarURL(user.getAvatarURL());
        }

        return userModelRepository.save(userActual);
    }

    public String matarUser(UUID id){
        Optional<UserModel> existeUser = userModelRepository.findById(id);
        if(existeUser.isEmpty()){
            throw new EntityNotFoundException("No se ha encontrado al usuario de ID " +id );
        }
        UserModel user = existeUser.get();
        userModelRepository.deleteById(id);

        return "Se ha eliminado al usuario: " + user.getUsername() + " " + user.getLastname() + ", de ID: " + user.getId();
    }

    
}


