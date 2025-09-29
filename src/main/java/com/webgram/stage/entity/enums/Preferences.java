package com.webgram.stage.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum Preferences {
    SPORT("Sport"),
    CUISINE("Cuisine"),
    LECTURE("Lecture");

    @Getter
    private final String description;

    Preferences(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Preferences fromValue(Object value) {
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
                Preferences.class.getSimpleName(), value, values()));
    }

    private static Preferences getByName(String name) {
        String upper = name.trim().toUpperCase();
        for (Preferences pref : values()) {
            if (pref.name().equals(upper) || pref.getDescription().equalsIgnoreCase(name.trim())) {
                return pref;
            }
        }
        throw new IllegalArgumentException("Invalid value for Preferences: " + name);
    }

    @JsonValue
    public Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<Preferences> getAllPreferences() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
