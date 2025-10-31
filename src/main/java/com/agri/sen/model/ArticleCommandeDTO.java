package com.agri.sen.model;

import com.agri.sen.entity.CommandeEntity;
import com.agri.sen.entity.ProduitEntity;
import lombok.Data;

@Data
public class ArticleCommandeDTO {

    private Long id;

    private Integer quantite;

    private Double prixUnitaire;

    private Double prixTotal;

    private Long commandeId;
    private CommandeEntity commande;

    private Long produitId;
    private ProduitEntity produit;
}
