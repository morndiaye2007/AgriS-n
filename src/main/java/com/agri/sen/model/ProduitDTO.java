package com.agri.sen.model;

import com.agri.sen.entity.CategorieEntity;
import com.agri.sen.entity.UtilisateurEntity;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class ProduitDTO {

    private Long id;

    @NotEmpty
    private String nom;

    private String description;

    private Double prix;

    private Integer stock;

    private String imageUrl;

    private Boolean disponible;

    private LocalDateTime dateCreation;

    private LocalDateTime dateModification;

    private Long categorieId;
    private CategorieEntity categorie;

    private Long vendeurId;
    private UtilisateurEntity vendeur;
}
