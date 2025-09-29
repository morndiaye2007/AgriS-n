package com.webgram.stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fichiers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String chemin;
    private String type;
    private Long taille;
}