package com.agri.sen.services.Impl;

import com.agri.sen.entity.QRapportEntity;
import com.agri.sen.entity.RapportEntity;
import com.agri.sen.mapper.RapportMapper;
import com.agri.sen.model.RapportDTO;
import com.agri.sen.repository.RapportRepository;
import com.agri.sen.services.RapportService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RapportServiceImpl implements RapportService {

    private final RapportRepository repository;
    private final RapportMapper mapper;

    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("titre", "titre"),
            Map.entry("type", "type"),
            Map.entry("dateGeneration", "dateGeneration")
    );

    @Override
    public RapportDTO createRapport(RapportDTO rapportDTO) {
        var entity = mapper.asEntity(rapportDTO);
        var saved = repository.save(entity);
        return mapper.asDto(saved);
    }

    @Override
    public RapportDTO updateRapport(RapportDTO rapportDTO) {
        var entity = mapper.asEntity(rapportDTO);
        var updated = repository.save(entity);
        return mapper.asDto(updated);
    }

    @Override
    public void deleteRapport(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Rapport not found");
        }
        repository.deleteById(id);
    }

    @Override
    public RapportDTO getRapport(Long id) {
        Optional<RapportEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Rapport not found with id: " + id);
        }
        return mapper.asDto(entity.get());
    }

    @Override
    public Page<RapportDTO> getAllRapport(Map<String, String> searchParams, Pageable pageable) {
        return null;
    }

    @Override
    public void exportRapport(PrintWriter writer) {
        try {
            List<RapportEntity> rapports = repository.findAll();

            StatefulBeanToCsv<RapportEntity> beanToCsv = new StatefulBeanToCsvBuilder<RapportEntity>(writer)
                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            beanToCsv.write(rapports);
            writer.flush();
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error exporting rapports to CSV", e);
            throw new RuntimeException("Error exporting rapports", e);
        }
    }

    @Override
    public List<RapportDTO> importRapport(List<RapportDTO> rapports) {
        List<RapportEntity> entities = rapports.stream()
                .map(mapper::asEntity)
                .collect(Collectors.toList());

        List<RapportEntity> savedEntities = repository.saveAll(entities);

        return savedEntities.stream()
                .map(mapper::asDto)
                .collect(Collectors.toList());
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QRapportEntity.rapportEntity;

            if (searchParams.containsKey("titre"))
                booleanBuilder.and(qEntity.titre.containsIgnoreCase(searchParams.get("titre")));

            if (searchParams.containsKey("type"))
                booleanBuilder.and(qEntity.type.containsIgnoreCase(searchParams.get("type")));

            if (searchParams.containsKey("donnees"))
                booleanBuilder.and(qEntity.donnees.containsIgnoreCase(searchParams.get("donnees")));
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