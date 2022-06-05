package io.luciomarcel.catalog.admin.infrastructure.configuration.json;

import java.util.concurrent.Callable;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

public enum Json {
    INSTANCE;

    private final ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
    .dateFormat(new StdDateFormat())
    .featuresToDisable(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
            DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,
            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    .modules(new JavaTimeModule(), new Jdk8Module(), afterburnerModule())
    .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
    .build();

    public static ObjectMapper mapper() {
        return INSTANCE.mapper.copy();
    }

    public static String writeValueAsString(final Object value) {
        return invoke(() -> INSTANCE.mapper.writeValueAsString(value));
    }

    public static <T> T readValue(final String json, final Class<T> valueType) {
        return invoke(() -> INSTANCE.mapper.readValue(json, valueType));
    }

   
    private AfterburnerModule afterburnerModule() {
        var module = new AfterburnerModule();
        // make afterburner generate bytecode only for public getters and setters and
        // fields
        // Without this, Java 9+ complains of "Illegal reflective access by
        // com.fasterxml.jackson.databind.ObjectMapper"
        module.setUseValueClassLoader(false);
        return module;
    }

    private static <T> T invoke(final Callable<T> callable) {
        try {
            return callable.call();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
