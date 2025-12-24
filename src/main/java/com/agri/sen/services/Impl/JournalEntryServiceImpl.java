package com.agri.sen.services.Impl;

import com.agri.sen.mapper.JournalEntryMapper;
import com.agri.sen.services.JournalEntryService;
import com.querydsl.core.BooleanBuilder;
import com.agri.sen.entity.QJournalEntry;
import com.agri.sen.entity.JournalEntry;
import com.agri.sen.entity.Parcelle;
import com.agri.sen.entity.Utilisateur;
import com.agri.sen.entity.enums.TypeActivite;
import com.agri.sen.model.JournalEntryDTO;
import com.agri.sen.repository.JournalEntryRepository;
import com.agri.sen.repository.ParcelleRepository;
import com.agri.sen.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class JournalEntryServiceImpl implements JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final ParcelleRepository parcelleRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final JournalEntryMapper journalEntryMapper;

    @Override
    public JournalEntryDTO createEntry(JournalEntryDTO journalEntryDTO) {
        Parcelle parcelle = parcelleRepository.findById(journalEntryDTO.getParcelleId())
                .orElseThrow(() -> new RuntimeException("Parcelle non trouvée"));

        // Vérifier que la parcelle appartient à l'utilisateur actuel
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!parcelle.getAgriculteurId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette parcelle");
        }

        var entity = journalEntryMapper.asEntity(journalEntryDTO);
        entity.setParcelleId(journalEntryDTO.getParcelleId());

        var entitySave = journalEntryRepository.save(entity);
        return enrichJournalEntryDTO(journalEntryMapper.asDto(entitySave));
    }

    @Override
    public JournalEntryDTO updateEntry(JournalEntryDTO journalEntryDTO) {
        JournalEntry existing = journalEntryRepository.findById(journalEntryDTO.getId())
                .orElseThrow(() -> new RuntimeException("Entrée de journal non trouvée"));

        Parcelle parcelle = parcelleRepository.findById(existing.getParcelleId())
                .orElseThrow(() -> new RuntimeException("Parcelle non trouvée"));

        // Vérifier les permissions
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!parcelle.getAgriculteurId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette entrée");
        }

        var entityUpdate = journalEntryMapper.asEntity(journalEntryDTO);
        entityUpdate.setParcelleId(existing.getParcelleId());

        var updatedEntity = journalEntryRepository.save(entityUpdate);
        return enrichJournalEntryDTO(journalEntryMapper.asDto(updatedEntity));
    }

    @Override
    public void deleteEntry(Long id) {
        JournalEntry entry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrée de journal non trouvée"));

        Parcelle parcelle = parcelleRepository.findById(entry.getParcelleId())
                .orElseThrow(() -> new RuntimeException("Parcelle non trouvée"));

        // Vérifier les permissions
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!parcelle.getAgriculteurId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette entrée");
        }

        journalEntryRepository.deleteById(id);
    }

    @Override
    public JournalEntryDTO getEntry(Long id) {
        var entity = journalEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrée de journal non trouvée"));
        return enrichJournalEntryDTO(journalEntryMapper.asDto(entity));
    }

    @Override
    public Page<JournalEntryDTO> getAllEntries(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return journalEntryRepository.findAll(booleanBuilder, pageable)
                .map(entity -> enrichJournalEntryDTO(journalEntryMapper.asDto(entity)));
    }

    @Override
    public Page<JournalEntryDTO> getEntriesByParcelle(Long parcelleId, Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        var qEntity = QJournalEntry.journalEntry;
        booleanBuilder.and(qEntity.parcelleId.eq(parcelleId));
        buildSearch(searchParams, booleanBuilder);
        return journalEntryRepository.findAll(booleanBuilder, pageable)
                .map(entity -> enrichJournalEntryDTO(journalEntryMapper.asDto(entity)));
    }

    @Override
    public Page<JournalEntryDTO> getCurrentUserEntries(Map<String, String> searchParams, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        var booleanBuilder = new BooleanBuilder();
        var qEntity = QJournalEntry.journalEntry;

        // Récupérer les IDs des parcelles de l'utilisateur
        var parcelleIds = parcelleRepository.findByAgriculteurId(currentUser.getId())
                .stream()
                .map(Parcelle::getId)
                .toList();

        if (!parcelleIds.isEmpty()) {
            booleanBuilder.and(qEntity.parcelleId.in(parcelleIds));
        }

        buildSearch(searchParams, booleanBuilder);

        return journalEntryRepository.findAll(booleanBuilder, pageable)
                .map(entity -> enrichJournalEntryDTO(journalEntryMapper.asDto(entity)));
    }

    @Override
    public Double getTotalCost(Long parcelleId, LocalDateTime startDate, LocalDateTime endDate) {
        Double totalCost = journalEntryRepository.getTotalCostByParcelleIdAndDateActiviteBetween(parcelleId, startDate, endDate);
        return totalCost != null ? totalCost : 0.0;
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QJournalEntry.journalEntry;

            if (searchParams.containsKey("typeActivite")) {
                String typeStr = searchParams.get("typeActivite");
                TypeActivite type = TypeActivite.valueOf(typeStr.toUpperCase());
                booleanBuilder.and(qEntity.typeActivite.eq(type));
            }

            if (searchParams.containsKey("startDate")) {
                LocalDateTime startDate = LocalDateTime.parse(searchParams.get("startDate"),
                        DateTimeFormatter.ISO_DATE_TIME);
                booleanBuilder.and(qEntity.dateActivite.goe(startDate));
            }

            if (searchParams.containsKey("endDate")) {
                LocalDateTime endDate = LocalDateTime.parse(searchParams.get("endDate"),
                        DateTimeFormatter.ISO_DATE_TIME);
                booleanBuilder.and(qEntity.dateActivite.loe(endDate));
            }

            if (searchParams.containsKey("description"))
                booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));
        }
    }

    // Enrichir le DTO avec le nom de la parcelle
    private JournalEntryDTO enrichJournalEntryDTO(JournalEntryDTO dto) {
        if (dto.getParcelleId() != null) {
            parcelleRepository.findById(dto.getParcelleId()).ifPresent(parcelle -> {
                dto.setParcelleNom(parcelle.getNom());
            });
        }
        return dto;
    }
}