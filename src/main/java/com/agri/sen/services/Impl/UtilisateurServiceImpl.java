package com.agri.sen.services.Impl;

import com.agri.sen.services.UtilisateurService;
import com.querydsl.core.BooleanBuilder;
import com.agri.sen.entity.QUtilisateur;
import com.agri.sen.entity.enums.Role;
import com.agri.sen.mapper.UtilisateurMapper;
import com.agri.sen.model.UtilisateurDTO;
import com.agri.sen.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private PasswordEncoder passwordEncoder;

    public void UtilisateurService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UtilisateurDTO createUtilisateur(UtilisateurDTO utilisateurDTO) {
        // Vérifier si l'email existe déjà
        if (utilisateurRepository.existsByEmail(utilisateurDTO.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        // Vérifier si le téléphone existe déjà
        if (utilisateurDTO.getTelephone() != null &&
                utilisateurRepository.existsByTelephone(utilisateurDTO.getTelephone())) {
            throw new RuntimeException("Numéro de téléphone déjà utilisé");
        }

        var entity = utilisateurMapper.asEntity(utilisateurDTO);

//         Encoder le mot de passe
        if (utilisateurDTO.getMotDePasse() != null) {
            entity.setMot_de_passe(passwordEncoder.encode(utilisateurDTO.getMotDePasse()));
        }

        var entitySave = utilisateurRepository.save(entity);
        return utilisateurMapper.asDto(entitySave);
    }

    @Override
    public UtilisateurDTO updateUtilisateur(UtilisateurDTO utilisateurDTO) {
        var existingEntity = utilisateurRepository.findById(utilisateurDTO.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        var entityUpdate = utilisateurMapper.asEntity(utilisateurDTO);

        // Garder le mot de passe existant si non fourni
        if (utilisateurDTO.getMotDePasse() == null || utilisateurDTO.getMotDePasse().isEmpty()) {
            entityUpdate.setMot_de_passe(existingEntity.getMot_de_passe());
        } else {
            entityUpdate.setMot_de_passe(passwordEncoder.encode(utilisateurDTO.getMotDePasse()));
        }

        var updatedEntity = utilisateurRepository.save(entityUpdate);
        return utilisateurMapper.asDto(updatedEntity);
    }

    @Override
    public void deleteUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDTO getUtilisateur(Long id) {
        var entity = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return utilisateurMapper.asDto(entity);
    }

    @Override
    public UtilisateurDTO getUtilisateurByEmail(String email) {
        var entity = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return utilisateurMapper.asDto(entity);
    }

    @Override
    public UtilisateurDTO getCurrentUtilisateur() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return getUtilisateurByEmail(email);
    }

    @Override
    public Page<UtilisateurDTO> getAllUtilisateurs(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return utilisateurRepository.findAll(booleanBuilder, pageable)
                .map(utilisateurMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QUtilisateur.utilisateur;

            if (searchParams.containsKey("nom"))
                booleanBuilder.and(qEntity.nom.containsIgnoreCase(searchParams.get("nom")));

            if (searchParams.containsKey("prenom"))
                booleanBuilder.and(qEntity.prenom.containsIgnoreCase(searchParams.get("prenom")));

            if (searchParams.containsKey("email"))
                booleanBuilder.and(qEntity.email.containsIgnoreCase(searchParams.get("email")));

            if (searchParams.containsKey("telephone"))
                booleanBuilder.and(qEntity.telephone.containsIgnoreCase(searchParams.get("telephone")));

            if (searchParams.containsKey("ville"))
                booleanBuilder.and(qEntity.ville.containsIgnoreCase(searchParams.get("ville")));

            if (searchParams.containsKey("role")) {
                String roleStr = searchParams.get("role");
                Role role = Role.valueOf(roleStr.toUpperCase());
                booleanBuilder.and(qEntity.role.eq(role));
            }

            if (searchParams.containsKey("enabled")) {
                Boolean enabled = Boolean.parseBoolean(searchParams.get("enabled"));
                booleanBuilder.and(qEntity.enabled.eq(enabled));
            }
        }
    }
}