package com.usuarios.Demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.usuarios.Demo.dto.AdminLoginRequest;
import com.usuarios.Demo.dto.AdminLoginResponse;
import com.usuarios.Demo.dto.UserLoginRequest;
import com.usuarios.Demo.dto.UserLoginResponse;

import com.usuarios.Demo.model.AdminModel;
import com.usuarios.Demo.model.UserModel;

import com.usuarios.Demo.repository.IAdminModelRepository;
import com.usuarios.Demo.repository.IUserModelRepository;

import com.usuarios.Demo.security.JwtUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class AuthController {


    // Dependencias del controlador
    private final AuthenticationManager authenticationManager;
    private final IAdminModelRepository adminRepository;
    private final IUserModelRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    //Constructor con inyección
    public AuthController(
            AuthenticationManager authenticationManager,
            IAdminModelRepository adminRepository,
            IUserModelRepository userRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }


    //LOGIN ADMIN
    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminLoginRequest request) {

        //Buscar admin por email
        AdminModel admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Admin no encontrado"));

        
        if (!request.getPassword().equals(admin.getPassword())) {
                throw new BadCredentialsException("Credenciales incorrectas");
        }


        //Generar token JWT
        String token = jwtUtil.generateToken(admin);

        return ResponseEntity.ok(new AdminLoginResponse(token, admin));
    }


    //LOGIN USUARIO NORMAL
    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {

        //Buscar usuario por email
        UserModel user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

        //Comparar contraseña NORMAL (sin encode)
        if (!request.getPassword().equals(user.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        //Generar token JWT para usuario
        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new UserLoginResponse(token, user));
    }

}
