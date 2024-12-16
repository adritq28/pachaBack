package com.pacha.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

//     @Bean
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//                 .allowedOrigins("http://localhost:5432", "http://localhost:8080", "https://192.168.100.236:8080")
//                 .allowedMethods("GET", "POST", "PUT", "DELETE")
//                 .allowedHeaders("*")
//                 .allowCredentials(false); // Desactiva las credenciales
//     }
// }

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080",
                                "https://192.168.100.236:8080")
                        .allowedMethods("*");
                ;
            }
        };
    }
}
