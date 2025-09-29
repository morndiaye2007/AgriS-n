package com.webgram.stage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String libelle;
    @ManyToOne
    @JoinColumn(name = "agent_id")
    private AgentEntity agent;

}