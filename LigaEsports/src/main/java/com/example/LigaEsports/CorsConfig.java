package com.example.LigaEsports;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("aaaaaaaaa");
        registry.addMapping("/**") // aplica a todas as rotas
                .allowedOrigins("http://localhost:5173") // porta do Vite
                .allowedMethods("*") // todos os métodos
                .allowedHeaders("*") // todos os cabeçalhos
                .allowCredentials(false); // se usares sessão/cookie, põe true
    }
}
