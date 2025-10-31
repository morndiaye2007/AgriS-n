package com.agri.sen.model;

import com.agri.sen.entity.RapportEntity;
import com.agri.sen.entity.UtilisateurEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnalyseDTO {

    private Long id;

    private String metrique;

    private Double valeur;

    private String periode;

    private LocalDateTime dateCreation;

    private Long utilisateurId;
    private UtilisateurEntity utilisateur;

    private Long rapportId;
    private RapportEntity rapport;
}
