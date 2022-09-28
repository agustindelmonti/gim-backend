package gym.utils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "application.swagger.enabled", havingValue = "true")
@OpenAPIDefinition(
        info = @Info(title = "Gim API", version = "v1"),
        servers = {
                @Server(url = "http://localhost:8080", description = "Development"),
                @Server(url = "http://localhost:8080", description = "Production")})
@SecurityScheme(
        name = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        return new OpenAPI();
    }
}