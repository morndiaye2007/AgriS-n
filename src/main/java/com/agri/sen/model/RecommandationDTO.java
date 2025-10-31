package com.agri.sen.model;

import com.agri.sen.entity.RessourceEntity;
import lombok.Data;

@Data
public class RecommandationDTO {

    private Long id;

    private String region;

    private String saison;

    private String culturesRecommandees;

    private String description;

    private Long ressourceId;
    private RessourceEntity ressource;
}
