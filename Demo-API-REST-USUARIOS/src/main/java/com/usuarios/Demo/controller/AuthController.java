package com.usuarios.Demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    private final AuthenticationManager authenticationManager;
    private final IAdminModelRepository adminRepository;
    private final IUserModelRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          IAdminModelRepository adminRepository,
                          IUserModelRepository userRepository,
                          JwtUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // ===========================================
    // 🔐 LOGIN ADMIN
    // ===========================================
    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminLoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );

            AdminModel admin = adminRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Admin no encontrado"));

            String token = jwtUtil.generateToken(admin);

            return ResponseEntity.ok(new AdminLoginResponse(token, admin));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas");
        }
    }

    // ===========================================
    // 👤 LOGIN USUARIO NORMAL
    // ===========================================
    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );

            UserModel user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String token = jwtUtil.generateToken(user);

            return ResponseEntity.ok(new UserLoginResponse(token, user));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas");
        }
    }
}
