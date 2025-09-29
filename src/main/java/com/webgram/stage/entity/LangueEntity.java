package com.webgram.stage.entity;

//import com.webgram.stage.entity.enums.SexType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "langue")
@Entity
public class LangueEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_langue")
    private String code;

    @Column(name = "libelle")
    private String libelle;

}