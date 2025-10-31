package com.agri.sen.services.Impl;

import com.agri.sen.entity.AnalyseEntity;
import com.agri.sen.entity.QAnalyseEntity;
import com.agri.sen.mapper.AnalyseMapper;
import com.agri.sen.model.AnalyseDTO;
import com.agri.sen.repository.AnalyseRepository;
import com.agri.sen.services.AnalyseService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AnalyseServiceImpl implements AnalyseService {

    private final AnalyseRepository analyseRepository;
    private final AnalyseMapper analyseMapper;

    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("metrique", "metrique"),
            Map.entry("valeur", "valeur"),
            Map.entry("periode", "periode"),
            Map.entry("dateCreation", "dateCreation"),
            Map.entry("utilisateur", "utilisateur.nom"),
            Map.entry("rapport", "rapport.titre")
    );

    @Override
    public AnalyseDTO createAnalyse(AnalyseDTO dto) {
        var entity = analyseMapper.asEntity(dto);
        var saved = analyseRepository.save(entity);
        return analyseMapper.asDto(saved);
    }

    @Override
    public AnalyseDTO updateAnalyse(AnalyseDTO dto) {
        var entity = analyseMapper.asEntity(dto);
        var updated = analyseRepository.save(entity);
        return analyseMapper.asDto(updated);
    }

    @Override
    public void deleteAnalyse(Long id) {
        if (!analyseRepository.existsById(id))
            throw new RuntimeException("Analyse not found");
        analyseRepository.deleteById(id);
    }

    @Override
    public AnalyseDTO getAnalyse(Long id) {
        Optional<AnalyseEntity> entity = analyseRepository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Analyse not found with id: " + id);
        }
        return analyseMapper.asDto(entity.get());
    }

    @Override
    public AnalyseDTO getAllAnalyses(Map<String, String> searchParams, Pageable pageable) {
        return null;
    }


    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QAnalyseEntity.analyseEntity;

            if (searchParams.containsKey("metrique"))
                booleanBuilder.and(qEntity.metrique.containsIgnoreCase(searchParams.get("metrique")));

            if (searchParams.containsKey("periode"))
                booleanBuilder.and(qEntity.periode.containsIgnoreCase(searchParams.get("periode")));

            if (searchParams.containsKey("valeur")) {
                try {
                    Double valeur = Double.valueOf(searchParams.get("valeur"));
                    booleanBuilder.and(qEntity.valeur.eq(valeur));
                } catch (NumberFormatException e) {
                    // Log l'erreur ou ignorer la recherche par valeur si la valeur n'est pas valide
                }
            }
        }
    }

    private Pageable applySorting(Pageable pageable, Map<String, String> params) {
        // 1) Si le Pageable fourni contient déjà des informations de tri, on les normalise
        if (pageable != null && pageable.getSort() != null && pageable.getSort().isSorted()) {
            Sort normalized = normalizeSort(pageable.getSort());
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), normalized);
        }

        // 2) Sinon, on tente de lire les paramètres personnalisés de tri
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

        // 3) Fallback: tri par défaut (id DESC)
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