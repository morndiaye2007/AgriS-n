package com.agri.sen.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cultures")
@Entity
public class Culture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_culture", unique = true, nullable = false)
    private String nom;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "duree_de_croissance")
    private Integer dureeCroissance;

    @Column(name = "saison_optimale")
    private String saisonOptimale;

    @Column(name = "image_url")
    private String imageUrl;
}




