package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "categorie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorieEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_categorie", nullable = false)
    private String nom;

    @Column(name = "description_categorie")
    private String description;
}
