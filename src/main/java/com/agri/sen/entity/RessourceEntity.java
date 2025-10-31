package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ressource")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RessourceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "type_ressource")
    private String type; // CONSEIL, VIDEO, GUIDE

    @Column(name = "url")
    private String url;

    @Column(name = "region")
    private String region;

    @Column(name = "langue")
    private String langue; // WOLOF, FRANÇAIS

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private UtilisateurEntity auteur;
}
