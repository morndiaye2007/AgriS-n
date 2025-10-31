package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "recommandation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommandationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region")
    private String region;

    @Column(name = "saison")
    private String saison;

    @Column(name = "cultures_recommandees", columnDefinition = "TEXT")
    private String culturesRecommandees; // JSON ou texte

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ressource_id")
    private RessourceEntity ressource;
}
