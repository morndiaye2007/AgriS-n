package com.agri.sen.services.Impl;

import com.agri.sen.entity.PaiementEntity;
import com.agri.sen.entity.QPaiementEntity;
import com.agri.sen.mapper.PaiementMapper;
import com.agri.sen.model.PaiementDTO;
import com.agri.sen.repository.PaiementRepository;
import com.agri.sen.services.PaiementService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository repository;
    private final PaiementMapper mapper;

    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("moyenPaiement", "moyenPaiement"),
            Map.entry("montant", "montant"),
            Map.entry("datePaiement", "datePaiement"),
            Map.entry("statut", "statut"),
            Map.entry("referenceTransaction", "referenceTransaction"),
            Map.entry("commande", "commande.numeroCommande")
    );

    @Override
    public PaiementDTO createPaiement(PaiementDTO paiementDTO) {
        var entity = mapper.asEntity(paiementDTO);
        var saved = repository.save(entity);
        return mapper.asDto(saved);
    }

    @Override
    public PaiementDTO updatePaiement(PaiementDTO paiementDTO) {
        var entity = mapper.asEntity(paiementDTO);
        var updated = repository.save(entity);
        return mapper.asDto(updated);
    }

    @Override
    public void deletePaiement(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Paiement not found");
        }
        repository.deleteById(id);
    }

    @Override
    public PaiementDTO getPaiement(Long id) {
        Optional<PaiementEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Paiement not found with id: " + id);
        }
        return mapper.asDto(entity.get());
    }

    @Override
    public Page<PaiementDTO> getAllPaiements(Map<String, String> searchParams, Pageable pageable) {
        return null;
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QPaiementEntity.paiementEntity;

            if (searchParams.containsKey("moyenPaiement"))
                booleanBuilder.and(qEntity.moyenPaiement.containsIgnoreCase(searchParams.get("moyenPaiement")));

            if (searchParams.containsKey("statut"))
                booleanBuilder.and(qEntity.statut.containsIgnoreCase(searchParams.get("statut")));

            if (searchParams.containsKey("referenceTransaction"))
                booleanBuilder.and(qEntity.referenceTransaction.containsIgnoreCase(searchParams.get("referenceTransaction")));

            if (searchParams.containsKey("commandeId")) {
                try {
                    Long commandeId = Long.valueOf(searchParams.get("commandeId"));
                    booleanBuilder.and(qEntity.commande.id.eq(commandeId));
                } catch (NumberFormatException e) {
                    // Log l'erreur ou ignorer
                }
            }

            if (searchParams.containsKey("montantMin")) {
                try {
                    Double montantMin = Double.valueOf(searchParams.get("montantMin"));
                    booleanBuilder.and(qEntity.montant.goe(montantMin));
                } catch (NumberFormatException e) {
                    // Log l'erreur ou ignorer
                }
            }

            if (searchParams.containsKey("montantMax")) {
                try {
                    Double montantMax = Double.valueOf(searchParams.get("montantMax"));
                    booleanBuilder.and(qEntity.montant.loe(montantMax));
                } catch (NumberFormatException e) {
                    // Log l'erreur ou ignorer
                }
            }
        }
    }

    private Pageable applySorting(Pageable pageable, Map<String, String> params) {
        if (pageable != null && pageable.getSort() != null && pageable.getSort().isSorted()) {
            Sort normalized = normalizeSort(pageable.getSort());
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), normalized);
        }

        String sortBy = null;
        String sortParam = params != null ? params.get("sort") : null;
        if (sortParam != null && !sortParam.isBlank()) {
            sortBy = sortParam;
        } else if (params != null && params.containsKey("sortBy")) {
            sortBy = params.get("sortBy");
        }

        String direction = params != null ? params.getOrDefault("direction", params.getOrDefault("dir", "asc")) : "asc";

        if (sortBy != null && !sortBy.isBlank()) {
            List<Sort.Order> orders = new ArrayList<>();
            String[] parts = sortBy.split(";");
            for (String p : parts) {
                if (p == null || p.isBlank()) continue;
                String field = p;
                String dir = direction;

                if (p.contains(",")) {
                    String[] tmp = p.split(",");
                    field = tmp[0].trim();
                    if (tmp.length > 1) dir = tmp[1].trim();
                } else if (p.contains(":")) {
                    String[] tmp = p.split(":");
                    field = tmp[0].trim();
                    if (tmp.length > 1) dir = tmp[1].trim();
                }

                String mapped = SORT_MAPPING.getOrDefault(field, field);
                Sort.Order order = ("desc".equalsIgnoreCase(dir)) ? Sort.Order.desc(mapped) : Sort.Order.asc(mapped);
                orders.add(order);
            }
            Sort sort = Sort.by(orders);
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }

        Sort defaultSort = Sort.by(Sort.Order.desc("id"));
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort);
    }

    private Sort normalizeSort(Sort sort) {
        if (sort == null || sort.isUnsorted()) return Sort.unsorted();
        List<Sort.Order> normalized = new ArrayList<>();
        for (Sort.Order o : sort) {
            String mapped = SORT_MAPPING.getOrDefault(o.getProperty(), o.getProperty());
            Sort.Order newOrder = new Sort.Order(o.getDirection(), mapped);
            normalized.add(newOrder);
        }
        return Sort.by(normalized);
    }
}