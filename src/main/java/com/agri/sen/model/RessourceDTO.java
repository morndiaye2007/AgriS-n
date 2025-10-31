package com.agri.sen.model;

import com.agri.sen.entity.UtilisateurEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RessourceDTO {

    private Long id;

    private String titre;

    private String description;

    private String type;

    private String url;

    private String region;

    private String langue;

    private LocalDateTime dateCreation;

    private Long auteurId;
    private UtilisateurEntity auteur;
}
