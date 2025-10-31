package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "produit")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProduitEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_produit", nullable = false)
    private String nom;

    @Column(name = "description_produit")
    private String description;

    @Column(name = "prix_produit", nullable = false)
    private Double prix;

    @Column(name = "stock_produit")
    private Integer stock;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    // Relations
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private CategorieEntity categorie;

    @ManyToOne
    @JoinColumn(name = "vendeur_id")
    private UtilisateurEntity vendeur;
}
