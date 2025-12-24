package com.agri.sen.entity.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum Role {
    ADMIN("Admin"),
    ACHETEUR("Acheteur"),
    AGRICULTEUR("Agriculteur");


    @Getter
    @Setter
    private String description;

    Role(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Role fromValue(Object role) {
        if (role instanceof Map) {
            Map<String, Object> mapRole = (Map<String, Object>) role;
            if (mapRole.containsKey("name")) {
                return Role.valueOf(mapRole.get("name").toString());
            }
        }
        if (role instanceof String) {
            return Role.valueOf(role.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", Role.class, role, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<Role> getAllRole() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}

