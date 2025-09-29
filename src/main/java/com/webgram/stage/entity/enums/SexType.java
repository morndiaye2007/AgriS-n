package com.webgram.stage.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum SexType {
    MASCULIN("Masculin"),
    FEMININ("Feminin");

    @Getter
    private final String description;

    SexType(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SexType fromValue(Object value) {
        if (value instanceof Map) {
            Map<String, Object> mapValue = (Map<String, Object>) value;
            if (mapValue.containsKey("name")) {
                return getByName(mapValue.get("name").toString());
            }
        }
        if (value instanceof String) {
            return getByName(value.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]",
                SexType.class.getSimpleName(), value, values()));
    }

    private static SexType getByName(String name) {
        String upper = name.trim().toUpperCase();
        for (SexType sexType : values()) {
            if (sexType.name().equals(upper) || sexType.getDescription().equalsIgnoreCase(name.trim())) {
                return sexType;
            }
        }
        throw new IllegalArgumentException("Invalid value for SexType: " + name);
    }

    @JsonValue
    public Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<SexType> getAllSexe() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
