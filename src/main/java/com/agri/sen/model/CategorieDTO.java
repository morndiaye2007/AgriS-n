package com.agri.sen.model;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

@Data
public class CategorieDTO {

    private Long id;

    @NotEmpty
    private String nom;

    private String description;
}
