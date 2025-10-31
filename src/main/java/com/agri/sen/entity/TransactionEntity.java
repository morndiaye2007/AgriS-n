package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "type_transaction")
    private String type; // INCOME, EXPENSE

    @Column(name = "date_transaction")
    private LocalDateTime dateTransaction;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "paiement_id")
    private PaiementEntity paiement;
}
