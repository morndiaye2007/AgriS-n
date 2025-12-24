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
@Table(name = "parcelles")
@Entity
public class Parcelle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parcelle_nom", nullable = false)
    private String nom;

    @Column(name = "parcelle_superficie", nullable = false)
    private Double superficie;

    @Column(name = "parcelle_description")
    private String description;

    @Column(name = "agriculteur_id", nullable = false)
    private Long agriculteurId;


    @Column(name = "culture_id")
    private Long cultureId;

    @Column(name = "parcelle_latitude")
    private Double latitude;

    @Column(name = "parcelle_longitude")
    private Double longitude;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "ville")
    private String ville;

    @Column(name = "type_sol")
    private String typeSol;

    @Column(name = "source_eau")
    private String sourceEau;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "parcelle_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "parcelle_updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (active == null) active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
