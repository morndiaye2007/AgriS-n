package com.agri.sen.model;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

@Data
public class UtilisateurDTO {

    private Long id;

    @NotEmpty
    private String nom;

    @NotEmpty
    private String prenom;

    private String email;

    private String motDePasse;

    private String role;
}
