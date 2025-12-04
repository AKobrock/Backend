package com.usuarios.Demo.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.usuarios.Demo.model.AdminModel;
import com.usuarios.Demo.repository.IAdminModelRepository;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final IAdminModelRepository adminRepository;

    public AdminDetailsService(IAdminModelRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminModel admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin no encontrado con email: " + email));

        // role "ADMIN" → Spring lo transforma en autoridad "ROLE_ADMIN"
        return User.withUsername(admin.getEmail())
                .password(admin.getPassword())
                .roles(admin.getRol()) // asumes "ADMIN"
                .build();
    }
}
