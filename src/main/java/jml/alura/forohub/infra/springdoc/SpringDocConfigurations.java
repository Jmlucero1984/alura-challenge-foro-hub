package jml.alura.forohub.infra.springdoc;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components()
                .addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                        .bearerFormat("JWT")))

        .info(new Info()
                .title("API Foro Hub - Desafío Alura Latam")
                .description("API Rest de la aplicación Foro Hub - Sistema CRUD de Usuarios, Tópicos de discusión, sus Respuestas y los Cursos a los que pertenecen." )
                .contact(new Contact()
                        .name("Equipo Backend")
                        .email("jmlucero1984@gmail.com")));
    }

    @Bean
    public String message() {
        return "Bearer is working";
    }
}
