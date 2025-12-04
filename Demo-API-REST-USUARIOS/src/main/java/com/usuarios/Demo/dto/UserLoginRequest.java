package com.usuarios.Demo.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
