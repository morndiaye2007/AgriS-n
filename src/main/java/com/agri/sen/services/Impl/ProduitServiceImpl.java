package com.agri.sen.services.Impl;

import com.agri.sen.entity.ProduitEntity;
import com.agri.sen.entity.QProduitEntity;
import com.agri.sen.model.ProduitDTO;
import com.agri.sen.repository.ProduitRepository;
import com.agri.sen.services.ProduitService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;

import com.agri.sen.entity.enums.StatutType;
import com.agri.sen.mapper.ProduitMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j

@Service
@Transactional
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
   

    // Mapping des clés de tri exposées côté client vers les propriétés JPA (y compris relations)
    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("nom", "nom"),
            Map.entry("description", "description"),
            Map.entry("prix", "prix"),
            Map.entry("stock", "stock"),
            Map.entry("disponible", "disponible"),
            Map.entry("dateCreation", "dateCreation"),
            Map.entry("dateModification", "dateModification"),
            Map.entry("categorie", "categorie.nom"),
            Map.entry("categorieNom", "categorie.nom"),
            Map.entry("vendeur", "vendeur.nom")
    );

    @Override
    public ProduitDTO createProduit(ProduitDTO produitDTO) {
            // Sauvegarde
            var entity = produitMapper.asEntity(produitDTO);
            var entitySave = produitRepository.save(entity);
            return produitMapper.asDto(entitySave);
        }

    @Override
    public ProduitDTO updateProduit(ProduitDTO produitDTO) {
        var entityUpdate = produitMapper.asEntity(produitDTO);
        var updatedEntity = produitRepository.save(entityUpdate);
        return produitMapper.asDto(updatedEntity);

    }


    @Override
    public void deleteProduit(Long id) {
        if (!produitRepository.existsById(id)) {
            throw new RuntimeException("Produit not found");
        }
        produitRepository.deleteById(id);
    }

    @Override
    public ProduitDTO getProduit(Long id) {
        Optional<ProduitEntity> entity = produitRepository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Produit not found with id: " + id);
        }
        return produitMapper.asDto(entity.get());
    }

    @Override
    public Page<ProduitDTO> getAllProduit(Map<String, String> searchParams, Pageable pageable) {
        return null;
    }


    // Construit un Pageable avec Sort en mappant les clés côté front vers les propriétés JPA
    private Pageable applySorting(Pageable pageable, Map<String, String> params) {
        // 1) Si le Pageable fourni contient déjà des informations de tri, on les normalise
        if (pageable != null && pageable.getSort() != null && pageable.getSort().isSorted()) {
            Sort normalized = normalizeSort(pageable.getSort());
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), normalized);
        }

        // 2) Sinon, on tente de lire les paramètres personnalisés de tri
        String sortBy = null;
        String sortParam = params != null ? params.get("sort") : null; // ex: "nomComplet,asc" ou "pays,desc"
        if (sortParam != null && !sortParam.isBlank()) {
            sortBy = sortParam;
        } else if (params != null && params.containsKey("sortBy")) {
            sortBy = params.get("sortBy");
        }

        String direction = params != null ? params.getOrDefault("direction", params.getOrDefault("dir", "asc")) : "asc";

        if (sortBy != null && !sortBy.isBlank()) {
            // Support de plusieurs tris: sep par ";"
            List<Sort.Order> orders = new ArrayList<>();
            String[] parts = sortBy.split(";");
            for (String p : parts) {
                if (p == null || p.isBlank()) continue;
                String field = p;
                String dir = direction; // direction globale par défaut

                // Autoriser forme "field,asc" ou "field:asc"
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

    // Normalise les propriétés de tri existantes via le mapping (par ex. "pays" -> "pays.libelle")
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

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QProduitEntity.produitEntity;

            if (searchParams.containsKey("nom"))
                booleanBuilder.and(qEntity.nom.containsIgnoreCase(searchParams.get("nom")));

            if (searchParams.containsKey("description"))
                booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));

            if (searchParams.containsKey("prix")) {
                try {
                    Integer prix = Integer.valueOf(searchParams.get("prix"));
                    booleanBuilder.and(qEntity.prix.eq(Double.valueOf(prix)));
                } catch (NumberFormatException e) {
                    // Log l'erreur ou ignorer la recherche par âge si la valeur n'est pas valide
                }
            }

        }
    }

}