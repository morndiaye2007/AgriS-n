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

public enum TypeContrat {
    CDI("Contrat à durée indéterminée"),
    CDD("Contrat à durée déterminée"),
    STAGE("Stage"),
    INTERIM("Intérim");

    @Getter
    @Setter
    private String description;

    TypeContrat(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeContrat fromValue(Object value) {
        if (value instanceof Map) {
            Map<String, Object> mapValue = (Map<String, Object>) value;
            if (mapValue.containsKey("name")) {
                return TypeContrat.valueOf(mapValue.get("name").toString());
            }
        }
        if (value instanceof String) {
            return TypeContrat.valueOf(value.toString());
        }
        throw new IllegalArgumentException(
                MessageFormat.format("{0} not found with the value: {1} in [{2}]",
                        TypeContrat.class, value, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<TypeContrat> getAllContrats() {
        return stream(values())
                .collect(Collectors.toSet());
    }

}
