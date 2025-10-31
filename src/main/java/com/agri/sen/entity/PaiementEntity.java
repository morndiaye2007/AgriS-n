package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiement")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiementEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moyen_paiement")
    private String moyenPaiement; // ORANGE_MONEY, WAVE, BANK, etc.

    @Column(name = "montant")
    private Double montant;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @Column(name = "statut_paiement")
    private String statut; // PENDING, COMPLETED, FAILED

    @Column(name = "reference_transaction")
    private String referenceTransaction;

    @OneToOne
    @JoinColumn(name = "commande_id")
    private CommandeEntity commande;
}
