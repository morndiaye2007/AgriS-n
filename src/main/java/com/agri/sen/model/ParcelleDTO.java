package com.agri.sen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "utilisateur")
@Entity
public class ParcelleDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prenom;

    private String nom;

    private String email;

    private String motdepasse;

    private String telephone;

    private String localisation;

    private Long cultureId;

    private Long AgriculteurId;

    private String AgriculteurNom;

    private String CultureNom;

}
