package com.agri.sen.entity;

import com.agri.sen.entity.enums.TypeActivite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "journal_entries")
@Entity
public class JournalEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parcelle_id", nullable = false)
    private Long parcelleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_activite", nullable = false)
    private TypeActivite typeActivite;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_activite", nullable = false)
    private LocalDateTime dateActivite;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "quantite")
    private Double quantite;

    @Column(name = "unite")
    private String unite;

    @Column(name = "cout")
    private Double cout;

    @Column(name = "photos", length = 2000)
    private String photos;

    @Column(name = "documents", length = 2000)
    private String documents;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
