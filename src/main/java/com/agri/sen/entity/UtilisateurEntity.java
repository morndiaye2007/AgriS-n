package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "utilisateur")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;

    private String email;

    private String motDePasse;

    private String role; // AGRICULTEUR, ACHETEUR, ADMIN
}
