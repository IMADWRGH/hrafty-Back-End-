package com.hrafty.web_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Adjust the mapping pattern to match your API endpoints
                .allowedOrigins("http://localhost:4200")  // Allow requests from Angular development server
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed HTTP methods
                .allowedHeaders("*")  // Allowed headers
                .allowCredentials(true);  // Allow sending cookies
    }
}