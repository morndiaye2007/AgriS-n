package com.agri.sen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webgram.stage.entity.LangueEntity;
import com.webgram.stage.entity.PaysEntity;
import com.webgram.stage.entity.enums.*;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotEmpty

    private String nomComplet;

    private String matricule;

    private String motdepasse;

    private String email;

    private String description;

    private Integer age;
    private StatutType statutType;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    private LocalTime heure;

//    @Column(name = "sexe")
    private SexType sexe;

    private FichierDTO filiere;
    private PosteDTO poste;


    private List<Preferences> preferences;


    private String telephone;

    private PaysEntity pays;
    private Long paysId;

    private StatutAgent statutAgent;


    private TypeContrat typeContrat;
    private Double notes;


    private LangueEntity langue;

    private String fileName;


    private String fileType;

    private byte[] fileData;


    private String fileBase64;

}