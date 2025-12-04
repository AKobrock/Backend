package com.usuarios.Demo.dto;

import com.usuarios.Demo.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponse {
    private String token;
    private UserModel user;
}
