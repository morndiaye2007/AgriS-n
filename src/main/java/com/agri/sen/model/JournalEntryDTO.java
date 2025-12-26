package com.agri.sen.model;

import com.agri.sen.entity.enums.TypeActivite;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JournalEntryDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "L'ID de la parcelle est obligatoire")
    private Long parcelleId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String parcelleNom;

    @NotNull(message = "Le type d'activité est obligatoire")
    private TypeActivite typeActivite;

    @NotNull(message = "La date d'activité est obligatoire")
    private LocalDateTime dateActivite;

    @NotEmpty(message = "La description est obligatoire")
    private String description;

    private Double quantite;
    private String unite;
    private Double cout;

    // Au lieu de List<String>, on utilise une String avec séparateur
    private String photos;
    private String documents;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}