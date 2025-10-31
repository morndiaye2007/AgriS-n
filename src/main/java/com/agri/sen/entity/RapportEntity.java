package com.agri.sen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "rapport")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RapportEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "type_rapport")
    private String type; // PERFORMANCE, SALES, FINANCIAL

    @Column(name = "donnees", columnDefinition = "TEXT")
    private String donnees; // JSON possible

    @Column(name = "date_generation")
    private LocalDateTime dateGeneration;
}
