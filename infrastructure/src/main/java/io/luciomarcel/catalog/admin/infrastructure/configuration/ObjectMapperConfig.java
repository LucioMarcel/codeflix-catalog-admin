package io.luciomarcel.catalog.admin.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.luciomarcel.catalog.admin.infrastructure.configuration.json.Json;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper  objectMapper(){
        return Json.mapper();
    }

}
