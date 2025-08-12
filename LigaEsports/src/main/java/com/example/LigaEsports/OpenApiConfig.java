package com.example.LigaEsports;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ligaEsportsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Liga Esports API")
                        .description("Documentação interativa da API da aplicação Liga Esports")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Suporte Liga Esports")
                                .email("suporte@ligaesports.com"))
                        .license(new License()
                                .name("Licença Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório GitHub")
                        .url("https://github.com/LuisRibeiro06/LigaEsports"));
    }
}
