package com.agri.sen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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
public class MeteoDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "La latitude est obligatoire")
    private Double latitude;

    @NotNull(message = "La longitude est obligatoire")
    private Double longitude;

    private String ville;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double temperature;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double temperatureMin;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double temperatureMax;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer humidite;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double vitesseVent;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String icone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double probabilitePluie;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime datePrevisionnelle;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
}