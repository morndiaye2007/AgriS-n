package com.agri.sen.entity;

import jakarta.persistence.*;
import com.agri.sen.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "utilisateur")
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "motdepasse", nullable = false)
    private String mot_de_passe;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "telephone", unique = true)
    private String telephone;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "ville")
    private String ville;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "profile_image")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "user_enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "user_last_login")
    private LocalDateTime lastLogin;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (enabled == null) enabled = true;
        if (role == null) role = Role.AGRICULTEUR;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
