package com.agri.sen.services.Impl;

import com.agri.sen.entity.ArticleCommandeEntity;
import com.agri.sen.entity.QArticleCommandeEntity;
import com.agri.sen.mapper.ArticleCommandeMapper;
import com.agri.sen.model.ArticleCommandeDTO;
import com.agri.sen.repository.ArticleCommandeRepository;
import com.agri.sen.services.ArticleCommandeService;
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
public class ArticleCommandeServiceImpl implements ArticleCommandeService {

    private final ArticleCommandeRepository articleCommandeRepository;
    private final ArticleCommandeMapper articleCommandeMapper;

    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("quantite", "quantite"),
            Map.entry("prixUnitaire", "prixUnitaire"),
            Map.entry("prixTotal", "prixTotal"),
            Map.entry("commande", "commande.id"),
            Map.entry("produit", "produit.nom")
    );

    @Override
    public ArticleCommandeDTO createArticleCommande(ArticleCommandeDTO dto) {
        var entity = articleCommandeMapper.asEntity(dto);
        var saved = articleCommandeRepository.save(entity);
        return articleCommandeMapper.asDto(saved);
    }

    @Override
    public ArticleCommandeDTO updateArticleCommande(ArticleCommandeDTO dto) {
        var entity = articleCommandeMapper.asEntity(dto);
        var updated = articleCommandeRepository.save(entity);
        return articleCommandeMapper.asDto(updated);
    }

    @Override
    public void deleteArticleCommande(Long id) {
        if (!articleCommandeRepository.existsById(id))
            throw new RuntimeException("ArticleCommande not found");
        articleCommandeRepository.deleteById(id);
    }

    @Override
    public ArticleCommandeDTO getArticleCommande(Long id) {
        Optional<ArticleCommandeEntity> entity = articleCommandeRepository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("ArticleCommande not found with id: " + id);
        }
        return articleCommandeMapper.asDto(entity.get());
    }

    @Override
    public Page<ArticleCommandeDTO> getAllArticleCommande(Map<String, String> searchParams, Pageable pageable) {
        return null;
    }


    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QArticleCommandeEntity.articleCommandeEntity;

            if (searchParams.containsKey("produit"))
                booleanBuilder.and(qEntity.produit.nom.containsIgnoreCase(searchParams.get("produit")));

            if (searchParams.containsKey("commandeId")) {
                try {
                    Long idCmd = Long.parseLong(searchParams.get("commandeId"));
                    booleanBuilder.and(qEntity.commande.id.eq(idCmd));
                } catch (NumberFormatException e) {
                    // Log l'erreur ou ignorer
                }
            }

            if (searchParams.containsKey("quantite")) {
                try {
                    Integer quantite = Integer.valueOf(searchParams.get("quantite"));
                    booleanBuilder.and(qEntity.quantite.eq(quantite));
                } catch (NumberFormatException e) {
                    // Log l'erreur ou ignorer
                }
            }

            if (searchParams.containsKey("prixMin")) {
                try {
                    Double prixMin = Double.parseDouble(searchParams.get("prixMin"));
                    booleanBuilder.and(qEntity.prixTotal.goe(prixMin));
                } catch (NumberFormatException e) {
                    // Log l'erreur ou ignorer
                }
            }

            if (searchParams.containsKey("prixMax")) {
                try {
                    Double prixMax = Double.parseDouble(searchParams.get("prixMax"));
                    booleanBuilder.and(qEntity.prixTotal.loe(prixMax));
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