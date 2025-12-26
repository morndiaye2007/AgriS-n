package com.agri.sen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest implements Serializable {

    @NotEmpty(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotEmpty(message = "Le mot de passe est obligatoire")
    private String motDePasse;
}
