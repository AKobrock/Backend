package com.usuarios.Demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuarios.Demo.model.UserModel;
import com.usuarios.Demo.service.UserModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;




//PAra trabajar con Json debemos utilizar restcontroller
@RestController //difiere de controller en que restcontroller habilita todo, contorller solo permite get y post
@RequestMapping("/api/v1/users")
public class UserModelController {

    private final UserModelService userModelService;
    public UserModelController(UserModelService userModelService) {
        this.userModelService = userModelService;
    }

    @GetMapping("/")
    public List<UserModel> getAllUsers (){
        return userModelService.getAllUsers();
    }

    @GetMapping("/users/id")
    public UserModel getUserId(@RequestParam UUID id) {
        return userModelService.getUserId(id);
    }

    @PostMapping("/users")
    public UserModel createUser(@RequestBody UserModel user) {
        return userModelService.CreateUser(user);
    }
    

    @PutMapping("users/{id}")
    public UserModel ActualizarUser(@PathVariable UUID id, @RequestBody UserModel user) {
        UserModel existingUserModel =  userModelService.getUserId(id);
        if (existingUserModel != null){
            existingUserModel.setPassword(user.getPassword());
        }
        
        return entity;
    }

    

    //Otros endpoints relacionados con usermoden puede sser añadidos aqui (post, put, delete etc)
}
