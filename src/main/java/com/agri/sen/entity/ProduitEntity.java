package com.agri.sen.entity;

import com.webgram.stage.entity.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agent")
@Entity
public class AgentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_complet")
    private String nomComplet;

    @Column(name = "matricule", unique = true, nullable = false)

    private String matricule;

    @Column(name = "mot_de_passe")
    private String motdepasse;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "age")
    private Integer age;

    @Column(name = "date_de_naissance")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Column(name = "type_statut")
    @Enumerated(EnumType.STRING)
    private StatutType statutType;

    @Column(name = "heure")
    private LocalTime heure;

    @Enumerated (EnumType.STRING)
    @Column(name = "sexe")
    private SexType sexe;

    @ElementCollection(targetClass = Preferences.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "agent_preferences", joinColumns = @JoinColumn(name = "agent_id"))
    @Column(name = "preference")
    private List<Preferences> preferences;



    @Column(name = "telephone")
    private String telephone;

    @ManyToOne
    @JoinColumn(name="pays_linked_agent")
    private PaysEntity pays;

    @Enumerated (EnumType.STRING)
    @Column(name = "statutAgent")
    private StatutAgent statutAgent;

    @Enumerated (EnumType.STRING)
    @Column(name = "typecontrat")
    private TypeContrat typeContrat;

    @ManyToOne
    @JoinColumn(name="langue_linked_agent")
    private LangueEntity langue;

    // Nouveau: lien vers le poste occupé
    @ManyToOne
    @JoinColumn(name = "poste_linked_agent")
    private PosteEntity poste;

    // Nouveau: lien vers la filière suivie
    @ManyToOne
    @JoinColumn(name = "filiere_linked_agent")
    private FilieresSuiviEntity filiereSuivi;

    private Double notes;

    @ManyToOne(fetch = FetchType.EAGER) // Change to EAGER or handle in service
    @JoinColumn(name = "filiere_id")
    private FilieresSuiviEntity filiere;



    //pour Ficher
//    @Column(name="Logo", nullable = true, length = 5000)
//    private String logo;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_data")
    private byte[] fileData;

    @Transient
    private String fileBase64;

}