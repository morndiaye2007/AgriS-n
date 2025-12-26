package com.agri.sen.services.Impl;

import com.agri.sen.services.ParcelleService;
import com.querydsl.core.BooleanBuilder;
import com.agri.sen.entity.QParcelle;
import com.agri.sen.entity.Parcelle;
import com.agri.sen.entity.Utilisateur;
import com.agri.sen.mapper.ParcelleMapper;
import com.agri.sen.model.ParcelleDTO;
import com.agri.sen.repository.ParcelleRepository;
import com.agri.sen.repository.UtilisateurRepository;
import com.agri.sen.repository.CultureRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ParcelleServiceImpl implements ParcelleService {

    private final ParcelleRepository parcelleRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final CultureRepository cultureRepository;
    private final ParcelleMapper parcelleMapper;

    @Override
    public ParcelleDTO createParcelle(ParcelleDTO parcelleDTO) {
        // Récupérer l'utilisateur actuel
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        var entity = parcelleMapper.asEntity(parcelleDTO);
        entity.setAgriculteurId(currentUser.getId());

        // Vérifier que la culture existe si fournie
        if (parcelleDTO.getCultureId() != null) {
            if (!cultureRepository.existsById(parcelleDTO.getCultureId())) {
                throw new RuntimeException("Culture non trouvée");
            }
            entity.setCultureId(parcelleDTO.getCultureId());
        }

        var entitySave = parcelleRepository.save(entity);
        return enrichParcelleDTO(parcelleMapper.asDto(entitySave));
    }

    @Override
    public ParcelleDTO updateParcelle(ParcelleDTO parcelleDTO) {
        Parcelle existing = parcelleRepository.findById(parcelleDTO.getId())
                .orElseThrow(() -> new RuntimeException("Parcelle non trouvée"));

        var entityUpdate = parcelleMapper.asEntity(parcelleDTO);
        entityUpdate.setAgriculteurId(existing.getAgriculteurId());

        // Vérifier que la culture existe si fournie
        if (parcelleDTO.getCultureId() != null) {
            if (!cultureRepository.existsById(parcelleDTO.getCultureId())) {
                throw new RuntimeException("Culture non trouvée");
            }
            entityUpdate.setCultureId(parcelleDTO.getCultureId());
        }

        var updatedEntity = parcelleRepository.save(entityUpdate);
        return enrichParcelleDTO(parcelleMapper.asDto(updatedEntity));
    }

    @Override
    public void deleteParcelle(Long id) {
        if (!parcelleRepository.existsById(id)) {
            throw new RuntimeException("Parcelle non trouvée");
        }
        parcelleRepository.deleteById(id);
    }

    @Override
    public ParcelleDTO getParcelle(Long id) {
        var entity = parcelleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parcelle non trouvée"));
        return enrichParcelleDTO(parcelleMapper.asDto(entity));
    }

    @Override
    public Page<ParcelleDTO> getAllParcelles(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return parcelleRepository.findAll(booleanBuilder, pageable)
                .map(entity -> enrichParcelleDTO(parcelleMapper.asDto(entity)));
    }

    @Override
    public Page<ParcelleDTO> getCurrentUserParcelles(Map<String, String> searchParams, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        var booleanBuilder = new BooleanBuilder();
        var qEntity = QParcelle.parcelle;
        booleanBuilder.and(qEntity.agriculteurId.eq(currentUser.getId()));
        buildSearch(searchParams, booleanBuilder);

        return parcelleRepository.findAll(booleanBuilder, pageable)
                .map(entity -> enrichParcelleDTO(parcelleMapper.asDto(entity)));
    }

    @Override
    public Double getTotalSuperficie(Long agriculteurId) {
        Double total = parcelleRepository.getTotalSuperficieByAgriculteur(agriculteurId);
        return total != null ? total : 0.0;
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QParcelle.parcelle;

            if (searchParams.containsKey("nom"))
                booleanBuilder.and(qEntity.nom.containsIgnoreCase(searchParams.get("nom")));

            if (searchParams.containsKey("ville"))
                booleanBuilder.and(qEntity.ville.containsIgnoreCase(searchParams.get("ville")));

            if (searchParams.containsKey("typeSol"))
                booleanBuilder.and(qEntity.typeSol.containsIgnoreCase(searchParams.get("typeSol")));

            if (searchParams.containsKey("active")) {
                Boolean active = Boolean.parseBoolean(searchParams.get("active"));
                booleanBuilder.and(qEntity.active.eq(active));
            }

            if (searchParams.containsKey("cultureId")) {
                Long cultureId = Long.parseLong(searchParams.get("cultureId"));
                booleanBuilder.and(qEntity.cultureId.eq(cultureId));
            }

            if (searchParams.containsKey("agriculteurId")) {
                Long agriculteurId = Long.parseLong(searchParams.get("agriculteurId"));
                booleanBuilder.and(qEntity.agriculteurId.eq(agriculteurId));
            }
        }
    }

    // Enrichir le DTO avec les noms de l'agriculteur et de la culture
    private ParcelleDTO enrichParcelleDTO(ParcelleDTO dto) {
        if (dto.getAgriculteurId() != null) {
            utilisateurRepository.findById(dto.getAgriculteurId()).ifPresent(user -> {
                dto.setAgriculteurNom(user.getNom() + " " + user.getPrenom());
            });
        }

        if (dto.getCultureId() != null) {
            cultureRepository.findById(dto.getCultureId()).ifPresent(culture -> {
                dto.setCultureNom(culture.getNom());
            });
        }

        return dto;
    }
}