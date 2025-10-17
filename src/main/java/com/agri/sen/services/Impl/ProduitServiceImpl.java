package com.agri.sen.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.stage.entity.AgentEntity;
import com.webgram.stage.entity.QAgentEntity;
import com.webgram.stage.entity.enums.Preferences;
import com.webgram.stage.entity.enums.SexType;
import com.webgram.stage.entity.enums.StatutType;
import com.webgram.stage.entity.enums.TypeContrat;
import com.webgram.stage.mapper.AgentMapper;
import com.webgram.stage.model.AgentDTO;
import com.webgram.stage.model.AgentExcelDTO;
import com.webgram.stage.repository.AgentRepository;
import com.webgram.stage.repository.LangueRepository;
import com.webgram.stage.repository.PaysRepository;
import com.webgram.stage.services.AgentService;
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
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final AgentMapper agentMapper;
    private final PaysRepository paysRepository;
    private final LangueRepository langueRepository;

    // Mapping des clés de tri exposées côté client vers les propriétés JPA (y compris relations)
    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("nomComplet", "nomComplet"),
            Map.entry("matricule", "matricule"),
            Map.entry("email", "email"),
            Map.entry("description", "description"),
            Map.entry("age", "age"),
            Map.entry("dateNaissance", "dateNaissance"),
            Map.entry("heure", "heure"),
            Map.entry("sexe", "sexe"),
            Map.entry("telephone", "telephone"),
            Map.entry("pays", "pays.libelle"),
            Map.entry("paysLibelle", "pays.libelle"),
            Map.entry("statutAgent", "statutAgent"),
            Map.entry("typeContrat", "typeContrat"),
            Map.entry("langue", "langue.libelle"),
            Map.entry("langueLibelle", "langue.libelle"),
            Map.entry("poste", "poste.intitule"),
            Map.entry("posteIntitule", "poste.intitule"),
            Map.entry("filiere", "filiereSuivi.libelle"),
            Map.entry("filiereLibelle", "filiereSuivi.libelle"),
            Map.entry("filiereCode", "filiereSuivi.code"),
            Map.entry("notes", "notes")
    );

    @Override
    public AgentDTO createAgent(AgentDTO agentDTO) {
            if (agentDTO.getMatricule() == null || agentDTO.getMatricule().isBlank()) {
                throw new IllegalArgumentException("Le matricule est obligatoire et doit être saisi par l’utilisateur.");
            }

            // Vérification : matricule unique
            if (agentRepository.existsByMatricule(agentDTO.getMatricule())) {
                throw new IllegalArgumentException(
                        "Le matricule " + agentDTO.getMatricule() + " existe déjà. Veuillez en saisir un autre."
                );
            }

            // Sauvegarde
            var entity = agentMapper.asEntity(agentDTO);
            var entitySave = agentRepository.save(entity);
            return agentMapper.asDto(entitySave);
        }

    @Override
    public AgentDTO updateAgent(AgentDTO agentDTO) {
        var entityUpdate = agentMapper.asEntity(agentDTO);
        var updatedEntity = agentRepository.save(entityUpdate);
        return agentMapper.asDto(updatedEntity);

    }


    @Override
    public void deleteAgent(Long id) {
        if (!agentRepository.existsById(id)) {
            throw new RuntimeException("Agent not found");
        }
        agentRepository.deleteById(id);
    }

    @Override
    public AgentDTO getAgent(Long id) {
        Optional<AgentEntity> entity = agentRepository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException("Agent not found with id: " + id);
        }
        return agentMapper.asDto(entity.get());
    }

    @Override
    public Page<AgentDTO> getAllAgents(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        Pageable effectivePageable = applySorting(pageable, searchParams);
        return agentRepository.findAll(booleanBuilder, effectivePageable)
                .map(agentMapper::asDto);
    }

    @Override
    public void exportAgent(PrintWriter writer) {
        writer.append(Arrays.stream(AgentExcelDTO.class.getDeclaredFields())
                        .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                        .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                        .map(f -> f.getAnnotation(CsvBindByName.class).column())
                        .collect(Collectors.joining(";")))
                .append("\n");

        StatefulBeanToCsv<AgentExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<AgentExcelDTO>(writer)

                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var agents = agentRepository.findAll().stream()
                .map(agentMapper::asExcelDto)
                .collect(Collectors.toList());

        try {
            beanToCsv.write(agents);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException("Export error", e);
        }
    }

    @Override
    public List<AgentDTO> importAgents(List<AgentDTO> agentDTOs) {
        log.info("Importing agents: {}", agentDTOs);
        try {
            List<AgentEntity> entities = agentDTOs.stream().map(dto -> {
                if (dto.getPaysId() != null && !paysRepository.existsById(dto.getPaysId())) {
                    throw new IllegalArgumentException("Invalid paysId: " + dto.getPaysId());
                }
                if (dto.getLangue() != null && !langueRepository.existsById(dto.getLangue().getId())) {
                    throw new IllegalArgumentException("Invalid langue id: " + dto.getLangue().getId());
                }
                if (agentRepository.existsByMatricule(dto.getMatricule())) {
                    throw new IllegalArgumentException("Matricule already exists: " + dto.getMatricule());
                }
                return agentMapper.asEntity(dto);
            }).collect(Collectors.toList());
            List<AgentEntity> savedEntities = agentRepository.saveAll(entities);
            return savedEntities.stream().map(agentMapper::asDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error importing agents", e);
            throw new RuntimeException("Failed to import agents: " + e.getMessage(), e);
        }
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

    private synchronized String generateUniqueMatricule() {
        Optional<String> lastMatriculeOpt = agentRepository.findLastMatricule();
        String newMatricule;

        if (lastMatriculeOpt.isPresent() && lastMatriculeOpt.get().matches("E\\d{4}")) {
            String lastMatricule = lastMatriculeOpt.get();
            int lastNumber = Integer.parseInt(lastMatricule.substring(1)); // "0001" de "E0001"
            int newNumber = lastNumber + 1;
            newMatricule = String.format("E%04d", newNumber); // Formate en "E0002"
        } else {
            newMatricule = "E0001"; // Premier matricule
        }
        return newMatricule;
    }


    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QAgentEntity.agentEntity;

            if (searchParams.containsKey("nomComplet"))
                booleanBuilder.and(qEntity.nomComplet.containsIgnoreCase(searchParams.get("nomComplet")));

            if (searchParams.containsKey("email"))
                booleanBuilder.and(qEntity.email.containsIgnoreCase(searchParams.get("email")));

            if (searchParams.containsKey("description"))
                booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));

            if (searchParams.containsKey("age")) {
                try {
                    Integer age = Integer.valueOf(searchParams.get("age"));
                    booleanBuilder.and(qEntity.age.eq(age));
                } catch (NumberFormatException e) {
                    // Log l'erreur ou ignorer la recherche par âge si la valeur n'est pas valide
                }
            }

            if (searchParams.containsKey("dateNaissance")) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(searchParams.get("dateNaissance"));
                    booleanBuilder.and(qEntity.dateNaissance.eq(date));
                } catch (ParseException e) {
                    // Log l'erreur ou ignorer la recherche par date si le format n'est pas valide
                }
            }


            if (searchParams.containsKey("heure")) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime time = LocalTime.parse(searchParams.get("heure"), formatter);
                    booleanBuilder.and(qEntity.heure.eq(time));
                } catch (DateTimeParseException e) {
                    // Logger l'erreur ou ignorer si format invalide
                }
            }

            if (searchParams.containsKey("sexe")) {
                try {
                    SexType sexType = SexType.valueOf(searchParams.get("sexe"));
                    booleanBuilder.and(qEntity.sexe.eq(sexType));
                } catch (IllegalArgumentException e) {
                    // Log l'erreur ou ignorer si la valeur enum n'est pas valide
                }
            }

            // Nouveau: Recherche par type de contrat (alias: contrat)
            if (searchParams.containsKey("typeContrat") || searchParams.containsKey("contrat")) {
                String contratStr = searchParams.getOrDefault("typeContrat", searchParams.get("contrat"));
                if (contratStr != null && !contratStr.isEmpty()) {
                    try {
                        TypeContrat typeContrat = TypeContrat.fromValue(contratStr);
                        booleanBuilder.and(qEntity.typeContrat.eq(typeContrat));
                    } catch (IllegalArgumentException e) {
                        // Log ou ignorer si la valeur n'est pas valide
                    }
                }
            }

            // Nouveau: Recherche par poste (id)
            if (searchParams.containsKey("posteId")) {
                String posteIdStr = searchParams.get("posteId");
                if (posteIdStr != null && !posteIdStr.isEmpty()) {
                    try {
                        Long posteId = Long.valueOf(posteIdStr);
                        booleanBuilder.and(qEntity.poste.id.eq(posteId));
                    } catch (NumberFormatException e) {
                        // ignorer si non numérique
                    }
                }
            }
            // Nouveau: Recherche par poste (intitulé) (alias: posteIntitule)
            if (searchParams.containsKey("poste") || searchParams.containsKey("posteIntitule")) {
                String posteIntitule = searchParams.getOrDefault("poste", searchParams.get("posteIntitule"));
                if (posteIntitule != null && !posteIntitule.isEmpty()) {
                    booleanBuilder.and(qEntity.poste.intitule.containsIgnoreCase(posteIntitule));
                }
            }

            // Nouveau: Recherche par filière suivie (id)
            if (searchParams.containsKey("filiereId")) {
                String filiereIdStr = searchParams.get("filiereId");
                if (filiereIdStr != null && !filiereIdStr.isEmpty()) {
                    try {
                        Long filiereId = Long.valueOf(filiereIdStr);
                        booleanBuilder.and(qEntity.filiereSuivi.id.eq(filiereId));
                    } catch (NumberFormatException e) {
                        // ignorer si non numérique
                    }
                }
            }
            // Nouveau: Recherche par filière suivie (libellé) (alias: filiere)
            if (searchParams.containsKey("filiereLibelle") || searchParams.containsKey("filiere")) {
                String lib = searchParams.getOrDefault("filiereLibelle", searchParams.get("filiere"));
                if (lib != null && !lib.isEmpty()) {
                    booleanBuilder.and(qEntity.filiereSuivi.libelle.containsIgnoreCase(lib));
                }
            }
            // Nouveau: Recherche par filière suivie (code)
            if (searchParams.containsKey("filiereCode")) {
                String code = searchParams.get("filiereCode");
                if (code != null && !code.isEmpty()) {
                    booleanBuilder.and(qEntity.filiereSuivi.code.containsIgnoreCase(code));
                }
            }

            // Nouveau: Recherche par filières multiples via IDs (CSV)
            if (searchParams.containsKey("filiereIds")) {
                String idsCsv = searchParams.get("filiereIds");
                if (idsCsv != null && !idsCsv.isEmpty()) {
                    try {
                        List<Long> idList = Arrays.stream(idsCsv.split(","))
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .map(Long::valueOf)
                                .collect(Collectors.toList());
                        if (!idList.isEmpty()) {
                            booleanBuilder.and(qEntity.filiereSuivi.id.in(idList));
                        }
                    } catch (NumberFormatException e) {
                        // ignorer si un des IDs n'est pas numérique
                    }
                }
            }
            if (searchParams.containsKey("preferences")) {
                String[] preferenceStrings = searchParams.get("preferences").split(",");
                List<Preferences> preferenceList = new ArrayList<>();

                for (String prefStr : preferenceStrings) {
                    try {
                        Preferences preference = Preferences.valueOf(prefStr.trim().toUpperCase());
                        preferenceList.add(preference);
                    } catch (IllegalArgumentException e) {
                        // Log ou ignorer la valeur non valide
                    }
                }

                if (!preferenceList.isEmpty()) {
                    booleanBuilder.and(qEntity.preferences.any().in(preferenceList));
                }
            }

            // Recherche par téléphone (égalité)
            if (searchParams.containsKey("telephone")) {
                String tel = searchParams.get("telephone");
                if (tel != null && !tel.isEmpty()) {
                    booleanBuilder.and(qEntity.telephone.eq(tel));
                }
            }

            // Recherche par matricule (contient, insensible à la casse)
            if (searchParams.containsKey("matricule")) {
                String matricule = searchParams.get("matricule");
                if (matricule != null && !matricule.isEmpty()) {
                    booleanBuilder.and(qEntity.matricule.containsIgnoreCase(matricule));
                }
            }

            // Recherche par notes (égalité)
            if (searchParams.containsKey("notes")) {
                String noteStr = searchParams.get("notes");
                if (noteStr != null && !noteStr.isEmpty()) {
                    try {
                        Double note = Double.valueOf(noteStr);
                        booleanBuilder.and(qEntity.notes.eq(note));
                    } catch (NumberFormatException e) {
                        // ignorer si non numérique
                    }
                }
            }

            // Recherche stricte: notes >, <, =
            if (searchParams.containsKey("notesGt")) {
                String s = searchParams.get("notesGt");
                if (s != null && !s.isEmpty()) {
                    try { booleanBuilder.and(qEntity.notes.gt(Double.valueOf(s))); } catch (NumberFormatException ignored) {}
                }
            }
            if (searchParams.containsKey("notesLt")) {
                String s = searchParams.get("notesLt");
                if (s != null && !s.isEmpty()) {
                    try { booleanBuilder.and(qEntity.notes.lt(Double.valueOf(s))); } catch (NumberFormatException ignored) {}
                }
            }
            if (searchParams.containsKey("notesEqual")) {
                String s = searchParams.get("notesEqual");
                if (s != null && !s.isEmpty()) {
                    try { booleanBuilder.and(qEntity.notes.eq(Double.valueOf(s))); } catch (NumberFormatException ignored) {}
                }
            }

            // Recherche par âge: min, max, égal, >, <
            if (searchParams.containsKey("ageMin")) {
                String s = searchParams.get("ageMin");
                if (s != null && !s.isEmpty()) {
                    try { booleanBuilder.and(qEntity.age.goe(Integer.valueOf(s))); } catch (NumberFormatException ignored) {}
                }
            }
            if (searchParams.containsKey("ageMax")) {
                String s = searchParams.get("ageMax");
                if (s != null && !s.isEmpty()) {
                    try { booleanBuilder.and(qEntity.age.loe(Integer.valueOf(s))); } catch (NumberFormatException ignored) {}
                }
            }
            if (searchParams.containsKey("ageEqual")) {
                String s = searchParams.get("ageEqual");
                if (s != null && !s.isEmpty()) {
                    try { booleanBuilder.and(qEntity.age.eq(Integer.valueOf(s))); } catch (NumberFormatException ignored) {}
                }
            }
            if (searchParams.containsKey("ageGt")) {
                String s = searchParams.get("ageGt");
                if (s != null && !s.isEmpty()) {
                    try { booleanBuilder.and(qEntity.age.gt(Integer.valueOf(s))); } catch (NumberFormatException ignored) {}
                }
            }
            if (searchParams.containsKey("ageLt")) {
                String s = searchParams.get("ageLt");
                if (s != null && !s.isEmpty()) {
                    try { booleanBuilder.and(qEntity.age.lt(Integer.valueOf(s))); } catch (NumberFormatException ignored) {}
                }
            }

            // Recherche par date de naissance: min, max, >, < (format yyyy-MM-dd)
            if (searchParams.containsKey("dateMin")) {
                String s = searchParams.get("dateMin");
                if (s != null && !s.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        booleanBuilder.and(qEntity.dateNaissance.goe(sdf.parse(s)));
                    } catch (ParseException ignored) {}
                }
            }
            if (searchParams.containsKey("dateMax")) {
                String s = searchParams.get("dateMax");
                if (s != null && !s.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        booleanBuilder.and(qEntity.dateNaissance.loe(sdf.parse(s)));
                    } catch (ParseException ignored) {}
                }
            }
            if (searchParams.containsKey("dateGt")) {
                String s = searchParams.get("dateGt");
                if (s != null && !s.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        booleanBuilder.and(qEntity.dateNaissance.gt(sdf.parse(s)));
                    } catch (ParseException ignored) {}
                }
            }
            if (searchParams.containsKey("dateLt")) {
                String s = searchParams.get("dateLt");
                if (s != null && !s.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        booleanBuilder.and(qEntity.dateNaissance.lt(sdf.parse(s)));
                    } catch (ParseException ignored) {}
                }
            }

            // Recherche par heure: min, max, >, < (format HH:mm)
            if (searchParams.containsKey("heureMin")) {
                String s = searchParams.get("heureMin");
                if (s != null && !s.isEmpty()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        booleanBuilder.and(qEntity.heure.goe(LocalTime.parse(s, formatter)));
                    } catch (DateTimeParseException ignored) {}
                }
            }
            if (searchParams.containsKey("heureMax")) {
                String s = searchParams.get("heureMax");
                if (s != null && !s.isEmpty()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        booleanBuilder.and(qEntity.heure.loe(LocalTime.parse(s, formatter)));
                    } catch (DateTimeParseException ignored) {}
                }
            }
            if (searchParams.containsKey("heureGt")) {
                String s = searchParams.get("heureGt");
                if (s != null && !s.isEmpty()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        booleanBuilder.and(qEntity.heure.gt(LocalTime.parse(s, formatter)));
                    } catch (DateTimeParseException ignored) {}
                }
            }
            if (searchParams.containsKey("heureLt")) {
                String s = searchParams.get("heureLt");
                if (s != null && !s.isEmpty()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        booleanBuilder.and(qEntity.heure.lt(LocalTime.parse(s, formatter)));
                    } catch (DateTimeParseException ignored) {}
                }
            }

            // Nouveau: Recherche par langue (id)
            if (searchParams.containsKey("langueId")) {
                String langueIdStr = searchParams.get("langueId");
                if (langueIdStr != null && !langueIdStr.isEmpty()) {
                    try {
                        Long langueId = Long.valueOf(langueIdStr);
                        booleanBuilder.and(qEntity.langue.id.eq(langueId));
                    } catch (NumberFormatException e) {
                        // ignorer si non numérique
                    }
                }
            }

            // Nouveau: Recherche par pays (id)
            if (searchParams.containsKey("paysId")) {
                String paysIdStr = searchParams.get("paysId");
                if (paysIdStr != null && !paysIdStr.isEmpty()) {
                    try {
                        Long paysId = Long.valueOf(paysIdStr);
                        booleanBuilder.and(qEntity.pays.id.eq(paysId));
                    } catch (NumberFormatException e) {
                        // ignorer si non numérique
                    }
                }
            }

            // Nouveau: Recherche par notes (min / max)
            if (searchParams.containsKey("notesMin") || searchParams.containsKey("notesMax")) {
                String minStr = searchParams.get("notesMin");
                String maxStr = searchParams.get("notesMax");
                try {
                    if (minStr != null && !minStr.isBlank()) {
                        Double min = Double.valueOf(minStr);
                        booleanBuilder.and(qEntity.notes.goe(min));
                    }
                    if (maxStr != null && !maxStr.isBlank()) {
                        Double max = Double.valueOf(maxStr);
                        booleanBuilder.and(qEntity.notes.loe(max));
                    }
                } catch (NumberFormatException e) {
                    // ignorer si format invalide
                }
            }
        }
    }

    @Override
    public AgentDTO updateStatut(Long id, StatutType statutType) {
        log.info("Mise à jour du statut avec ID={} à {}", id, statutType);
        if (id == null || statutType == null) {
            throw new IllegalArgumentException("L'ID et le typeStatut ne peuvent pas être null.");
        }
        AgentEntity entity = agentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("expression avec ID=" + id + " non trouvé."));
        entity.setStatutType(statutType);
        entity = agentRepository.save(entity);
        log.info("Statut  mis à jour avec succès : ID={}", entity.getId());
        return agentMapper.asDto(entity);
    }

}