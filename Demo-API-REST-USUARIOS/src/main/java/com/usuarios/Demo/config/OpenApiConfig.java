package com.usuarios.Demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI basOpenAPI(){

        return new OpenAPI().info(
            new Info()
                .title("API Aqui Papa")
                .description("Documentacion de la API de la pagina Aqui Papa")
                .version("v1.0.0")
                .contact(new Contact().name("Equipo Backend").email("back@end.org"))
        );

    }

}
