package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "article_commande")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommandeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantite")
    private Integer quantite;
    @Column(name = "prix_unitaire")
    private Double prixUnitaire;
    @Column(name = "prix_total")
    private Double prixTotal;
    @ManyToOne
    @JoinColumn(name = "commande_id")
    private CommandeEntity commande;
    @ManyToOne
    @JoinColumn(name = "produit_id")
    private ProduitEntity produit;
}
