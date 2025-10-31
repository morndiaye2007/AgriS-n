package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "commande")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_commande", unique = true)
    private String numeroCommande;

    @Column(name = "statut_commande")
    private String statut; // PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED

    @Column(name = "montant_total")
    private Double montantTotal;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @ManyToOne
    @JoinColumn(name = "acheteur_id")
    private UtilisateurEntity acheteur;

    @OneToOne(mappedBy = "commande")
    private PaiementEntity paiement;
}
