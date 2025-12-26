//package com.agri.sen.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import com.agri.sen.entity.enums.*;
//import jakarta.persistence.Temporal;
//import jakarta.persistence.TemporalType;
//import jakarta.validation.constraints.NotEmpty;
//import lombok.*;
//
//import java.io.Serializable;
//import java.util.Date;
//
//
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//@ToString
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class ProduitDTO implements Serializable {
//
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private Long id;
//    @NotEmpty
//
//    private String nom;
//
//    private String description;
//
//    private String categorie;
//
//    private Double prix;
//
//    private Double quantiteStock;
//
//
//
//
//
//
//
//
//}