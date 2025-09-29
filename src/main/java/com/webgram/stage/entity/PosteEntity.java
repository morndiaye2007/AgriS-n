package com.webgram.stage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intitule;

    // Poste lié à un département
    @ManyToOne
    @JoinColumn(name = "departement_id")
    private DepartementEntity departement;
}