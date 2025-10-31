package com.agri.sen.services.Impl;

import com.agri.sen.entity.CommandeEntity;
import com.agri.sen.entity.QCommandeEntity;
import com.agri.sen.mapper.CommandeMapper;
import com.agri.sen.model.CommandeDTO;
import com.agri.sen.repository.CommandeRepository;
import com.agri.sen.services.CommandeService;
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
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository repository;
    private final CommandeMapper mapper;

    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("numeroCommande", "numeroCommande"),
            Map.entry("statut", "statut"),
            Map.entry("montantTotal", "montantTotal"),
            Map.entry("dateCreation", "dateCreation"),
            Map.entry("dateModification", "dateModification"),
            Map.entry("acheteur", "acheteur.nom")
    );

    @Override
    public CommandeDTO createCommande(CommandeDTO commandeDTO) {
        var entity = mapper.asEntity(commandeDTO);
        var saved = repository.save(entity);
        return mapper.asDto(saved);
    }

    @Override
    public CommandeDTO updateCommande(CommandeDTO commandeDTO) {
        var entity = mapper.asEntity(commandeDTO);
        var updated = repository.save(entity);
        return mapper.asDto(updated);
    }

    @Override
    public void deleteCommande(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Commande not found");
        }
        repository.deleteById(id);
    }

    @Override
    public CommandeDTO getCommande(Long id) {
        Optional<CommandeEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Commande not found with id: " + id);
        }
        return mapper.asDto(entity.get());
    }

    @Override
    public Page<CommandeDTO> getAllCommandes(Map<String, String> searchParams, Pageable pageable) {
        return null;
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QCommandeEntity.commandeEntity;

            if (searchParams.containsKey("numeroCommande"))
                booleanBuilder.and(qEntity.numeroCommande.containsIgnoreCase(searchParams.get("numeroCommande")));

            if (searchParams.containsKey("statut"))
                booleanBuilder.and(qEntity.statut.containsIgnoreCase(searchParams.get("statut")));

            if (searchParams.containsKey("acheteur"))
                booleanBuilder.and(qEntity.acheteur.nom.containsIgnoreCase(searchParams.get("acheteur")));

            if (searchParams.containsKey("montantMin")) {
                try {
                    Double montantMin = Double.valueOf(searchParams.get("montantMin"));
                    booleanBuilder.and(qEntity.montantTotal.goe(montantMin));
                } catch (NumberFormatException e) {
                    // Log l'erreur ou ignorer
                }
            }

            if (searchParams.containsKey("montantMax")) {
                try {
                    Double montantMax = Double.valueOf(searchParams.get("montantMax"));
                    booleanBuilder.and(qEntity.montantTotal.loe(montantMax));
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