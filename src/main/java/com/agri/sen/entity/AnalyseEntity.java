package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "analyse")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metrique")
    private String metrique;

    @Column(name = "valeur")
    private Double valeur;

    @Column(name = "periode")
    private String periode; // exemple : "2025-Q1"

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private UtilisateurEntity utilisateur;

    @ManyToOne
    @JoinColumn(name = "rapport_id")
    private RapportEntity rapport;
}
