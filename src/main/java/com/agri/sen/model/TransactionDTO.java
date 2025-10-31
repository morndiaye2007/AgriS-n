package com.agri.sen.model;

import com.agri.sen.entity.PaiementEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Long id;

    private String reference;

    private Double montant;

    private String type;

    private LocalDateTime dateTransaction;

    private String description;

    private Long paiementId;
    private PaiementEntity paiement;
}
