package com.usuarios.Demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") //Todos los endspoints!!!!!!!!!
        .allowedOrigins("http://localhost:5173") //pemitir todas las fuentes
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //metodos permitidos
        .allowedHeaders("*") //Permitir todos los encabezados
        .allowCredentials(true); 
    }
}
