package com.usuarios.Demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket usuariosApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("Usuarios")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.usuarios.Demo.controller"))
            .paths(PathSelectors.ant("/api/v1/users/**"))
            .build()
            .apiInfo(apiInfo("Usuarios API", "Gestión de usuarios del sistema"));
    }

    @Bean
    public Docket administradoresApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("Administradores")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.usuarios.Demo.controller"))
            .paths(PathSelectors.ant("/api/v1/admins/**"))
            .build()
            .apiInfo(apiInfo("Administradores API", "Gestión de administradores del sistema"));
    }

    @Bean
    public Docket papasApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("Papas")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.usuarios.Demo.controller"))
            .paths(PathSelectors.ant("/api/v1/papas/**"))
            .build()
            .apiInfo(apiInfo("Papas API", "Gestión de papás disponibles para arriendo"));
    }

    private ApiInfo apiInfo(String title, String description) {
        return new ApiInfoBuilder()
            .title(title)
            .description(description)
            .version("1.0.0")
            .build();
    }
}

