package com.agri.sen.services.Impl;

import com.agri.sen.entity.CategorieEntity;
import com.agri.sen.entity.QCategorieEntity;
import com.agri.sen.mapper.CategorieMapper;
import com.agri.sen.model.CategorieDTO;
import com.agri.sen.repository.CategorieRepository;
import com.agri.sen.services.CategorieService;
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
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;
    private final CategorieMapper categorieMapper;

    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("nom", "nom"),
            Map.entry("description", "description")
    );

    @Override
    public CategorieDTO createCategorie(CategorieDTO dto) {
        var entity = categorieMapper.asEntity(dto);
        var saved = categorieRepository.save(entity);
        return categorieMapper.asDto(saved);
    }

    @Override
    public CategorieDTO updateCategorie(CategorieDTO dto) {
        var entity = categorieMapper.asEntity(dto);
        var updated = categorieRepository.save(entity);
        return categorieMapper.asDto(updated);
    }

    @Override
    public void deleteCategorie(Long id) {
        if (!categorieRepository.existsById(id))
            throw new RuntimeException("Categorie not found");
        categorieRepository.deleteById(id);
    }

    @Override
    public CategorieDTO getCategorie(Long id) {
        Optional<CategorieEntity> entity = categorieRepository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Categorie not found with id: " + id);
        }
        return categorieMapper.asDto(entity.get());
    }

    @Override
    public Page<CategorieDTO> getAllCategorie(Map<String, String> searchParams, Pageable pageable) {
        return null;
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QCategorieEntity.categorieEntity;

            if (searchParams.containsKey("nom"))
                booleanBuilder.and(qEntity.nom.containsIgnoreCase(searchParams.get("nom")));

            if (searchParams.containsKey("description"))
                booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));
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