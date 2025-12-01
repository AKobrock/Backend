package com.usuarios.Demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") // ✅ permite todo
                        .allowedMethods("*") // ✅ permite GET, POST, PUT, DELETE, etc.
                        .allowedHeaders("*"); // ✅ permite cualquier cabecera
            }
        };
    }
}
