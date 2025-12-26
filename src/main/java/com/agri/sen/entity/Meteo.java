package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meteo")
@Entity
public class Meteo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "ville")
    private String ville;

    @Column(name = "temperature", nullable = false)
    private Double temperature;

    @Column(name = "temperature_min")
    private Double temperatureMin;

    @Column(name = "temperature_max")
    private Double temperatureMax;

    @Column(name = "humidite")
    private Integer humidite;

    @Column(name = "vitesse_vent")
    private Double vitesseVent;

    @Column(name = "description")
    private String description;

    @Column(name = "icone")
    private String icone;

    @Column(name = "probabilite_pluie")
    private Double probabilitePluie;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_previsionnelle", nullable = false)
    private LocalDateTime datePrevisionnelle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
