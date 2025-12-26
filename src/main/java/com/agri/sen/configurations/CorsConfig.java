package com.agri.sen.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origines autorisées
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));

        // Méthodes HTTP autorisées
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Headers autorisés
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Headers exposés
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        // Autoriser les credentials (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // Durée de cache de la configuration CORS
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}