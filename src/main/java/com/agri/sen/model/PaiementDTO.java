package com.agri.sen.model;

import com.agri.sen.entity.CommandeEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaiementDTO {

    private Long id;

    private String moyenPaiement;

    private Double montant;

    private LocalDateTime datePaiement;

    private String statut;

    private String referenceTransaction;

    private Long commandeId;
    private CommandeEntity commande;
}
