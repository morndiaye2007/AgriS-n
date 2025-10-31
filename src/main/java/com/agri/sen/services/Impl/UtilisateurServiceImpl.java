package com.agri.sen.services.Impl;

import com.agri.sen.entity.QUtilisateurEntity;
import com.agri.sen.entity.UtilisateurEntity;
import com.agri.sen.mapper.UtilisateurMapper;
import com.agri.sen.model.UtilisateurDTO;
import com.agri.sen.repository.UtilisateurRepository;
import com.agri.sen.services.UtilisateurService;
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
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository repository;
    private final UtilisateurMapper mapper;

    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("nom", "nom"),
            Map.entry("prenom", "prenom"),
            Map.entry("email", "email"),
            Map.entry("role", "role")
    );

    @Override
    public UtilisateurDTO createUtilisateur(UtilisateurDTO utilisateurDTO) {
        var entity = mapper.asEntity(utilisateurDTO);
        var saved = repository.save(entity);
        return mapper.asDto(saved);
    }

    @Override
    public UtilisateurDTO updateUtilisateur(UtilisateurDTO utilisateurDTO) {
        var entity = mapper.asEntity(utilisateurDTO);
        var updated = repository.save(entity);
        return mapper.asDto(updated);
    }

    @Override
    public void deleteUtilisateur(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Utilisateur not found");
        }
        repository.deleteById(id);
    }

    @Override
    public UtilisateurDTO getUtilisateur(Long id) {
        Optional<UtilisateurEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Utilisateur not found with id: " + id);
        }
        return mapper.asDto(entity.get());
    }

    @Override
    public Page<UtilisateurDTO> getAllUtilisateurs(Map<String, String> searchParams, Pageable pageable) {
        return null;
    }


    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QUtilisateurEntity.utilisateurEntity;

            if (searchParams.containsKey("nom"))
                booleanBuilder.and(qEntity.nom.containsIgnoreCase(searchParams.get("nom")));

            if (searchParams.containsKey("prenom"))
                booleanBuilder.and(qEntity.prenom.containsIgnoreCase(searchParams.get("prenom")));

            if (searchParams.containsKey("email"))
                booleanBuilder.and(qEntity.email.containsIgnoreCase(searchParams.get("email")));

            if (searchParams.containsKey("role"))
                booleanBuilder.and(qEntity.role.containsIgnoreCase(searchParams.get("role")));
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