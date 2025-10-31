package com.agri.sen.services.Impl;

import com.agri.sen.entity.QRessourceEntity;
import com.agri.sen.entity.RessourceEntity;
import com.agri.sen.mapper.RessourceMapper;
import com.agri.sen.model.RessourceDTO;
import com.agri.sen.repository.RessourceRepository;
import com.agri.sen.services.RessourceService;
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
public class RessourceServiceImpl implements RessourceService {

    private final RessourceRepository repository;
    private final RessourceMapper mapper;

    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("titre", "titre"),
            Map.entry("description", "description"),
            Map.entry("type", "type"),
            Map.entry("url", "url"),
            Map.entry("region", "region"),
            Map.entry("langue", "langue"),
            Map.entry("dateCreation", "dateCreation"),
            Map.entry("auteur", "auteur.nom")
    );

    @Override
    public RessourceDTO createRessource(RessourceDTO ressourceDTO) {
        var entity = mapper.asEntity(ressourceDTO);
        var saved = repository.save(entity);
        return mapper.asDto(saved);
    }

    @Override
    public RessourceDTO updateRessource(RessourceDTO ressourceDTO) {
        var entity = mapper.asEntity(ressourceDTO);
        var updated = repository.save(entity);
        return mapper.asDto(updated);
    }

    @Override
    public void deleteRessource(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Ressource not found");
        }
        repository.deleteById(id);
    }

    @Override
    public RessourceDTO getRessource(Long id) {
        Optional<RessourceEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Ressource not found with id: " + id);
        }
        return mapper.asDto(entity.get());
    }

    @Override
    public Page<RessourceDTO> getAllRessources(Map<String, String> searchParams, Pageable pageable) {
        return null;
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QRessourceEntity.ressourceEntity;

            if (searchParams.containsKey("titre"))
                booleanBuilder.and(qEntity.titre.containsIgnoreCase(searchParams.get("titre")));

            if (searchParams.containsKey("description"))
                booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));

            if (searchParams.containsKey("type"))
                booleanBuilder.and(qEntity.type.containsIgnoreCase(searchParams.get("type")));

            if (searchParams.containsKey("region"))
                booleanBuilder.and(qEntity.region.containsIgnoreCase(searchParams.get("region")));

            if (searchParams.containsKey("langue"))
                booleanBuilder.and(qEntity.langue.containsIgnoreCase(searchParams.get("langue")));

            if (searchParams.containsKey("url"))
                booleanBuilder.and(qEntity.url.containsIgnoreCase(searchParams.get("url")));

            if (searchParams.containsKey("auteurId")) {
                try {
                    Long auteurId = Long.valueOf(searchParams.get("auteurId"));
                    booleanBuilder.and(qEntity.auteur.id.eq(auteurId));
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