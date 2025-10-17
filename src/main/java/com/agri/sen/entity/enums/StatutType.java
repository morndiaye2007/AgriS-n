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

public enum StatutType {
    TRAITEMENT_ENCOUR("En cours..."),
    ACCEPTER("Accepté"),
    REFUSER("Rejeté"),
    FERMER("Fermé");

    @Getter
    private final String description;

    StatutType(String description) {
        this.description = description;
    }

    @JsonCreator
    public static StatutType fromValue(Object value) {
        if (value instanceof Map map && map.containsKey("name")) {
            return StatutType.valueOf(map.get("name").toString());
        }
        if (value instanceof String str) {
            return StatutType.valueOf(str);
        }
        throw new IllegalArgumentException("StatutType invalide: " + value);
    }

    @JsonValue
    public Map<String, Object> toJson() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }
}
