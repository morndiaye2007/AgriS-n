package com.agri.sen.model;

import com.agri.sen.entity.PaiementEntity;
import com.agri.sen.entity.UtilisateurEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommandeDTO {

    private Long id;

    private String numeroCommande;

    private String statut;

    private Double montantTotal;

    private LocalDateTime dateCreation;

    private LocalDateTime dateModification;

    private Long acheteurId;
    private UtilisateurEntity acheteur;

    private PaiementEntity paiement;
}
