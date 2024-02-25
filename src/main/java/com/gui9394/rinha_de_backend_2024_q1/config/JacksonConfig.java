package com.gui9394.rinha_de_backend_2024_q1.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_FLOAT_AS_INT;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return customizer -> customizer.propertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
                .postConfigurer(objectMapper -> objectMapper.configure(ACCEPT_FLOAT_AS_INT, false));
    }

}
