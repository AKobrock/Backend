package com.usuarios.Demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserModel {
//Representa nuestra identidad en la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; 
    private String username;
    private String lastname;
    private String rut;
    @Column(nullable = true)
    private String avatarURL;
    private String address;
    private String email;
    private String password;
    private Boolean active;
    private LocalDateTime lastActivity; //Toma la muestra de la última vez que ingresó a la cuenta o hubo movimiento.
    private String rol = "USER";
}
