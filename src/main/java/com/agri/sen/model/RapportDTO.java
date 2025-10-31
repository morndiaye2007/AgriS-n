package com.agri.sen.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RapportDTO {

    private Long id;

    private String titre;

    private String type;

    private String donnees;

    private LocalDateTime dateGeneration;
}
