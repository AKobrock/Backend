package com.usuarios.Demo.dto;

import com.usuarios.Demo.model.AdminModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminLoginResponse {
    private String token;
    private AdminModel admin;
}
