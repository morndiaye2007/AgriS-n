package com.webgram.stage.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum StatutAgent {
    ACTIF("Actif"),
    CONGE("En congé"),
    SUSPENDU("Suspendu"),
    SORTI("Sorti");

    @Getter
    @Setter
    private String description;

    StatutAgent(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StatutAgent fromValue(Object value) {
        if (value instanceof Map) {
            Map<String, Object> mapValue = (Map<String, Object>) value;
            if (mapValue.containsKey("name")) {
                return StatutAgent.valueOf(mapValue.get("name").toString());
            }
        }
        if (value instanceof String) {
            return StatutAgent.valueOf(value.toString());
        }
        throw new IllegalArgumentException(
                MessageFormat.format("{0} not found with the value: {1} in [{2}]",
                        StatutAgent.class, value, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<StatutAgent> getAllStatuts() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
