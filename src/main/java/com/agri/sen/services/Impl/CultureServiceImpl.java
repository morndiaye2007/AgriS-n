package com.agri.sen.services.Impl;

import com.agri.sen.services.CultureService;
import com.querydsl.core.BooleanBuilder;
import com.agri.sen.entity.QCulture;
import com.agri.sen.mapper.CultureMapper;
import com.agri.sen.model.CultureDTO;
import com.agri.sen.repository.CultureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CultureServiceImpl implements CultureService {

    private final CultureRepository cultureRepository;
    private final CultureMapper cultureMapper;

    @Override
    public CultureDTO createCulture(CultureDTO cultureDTO) {
        var entity = cultureMapper.asEntity(cultureDTO);
        var entitySave = cultureRepository.save(entity);
        return cultureMapper.asDto(entitySave);
    }

    @Override
    public CultureDTO updateCulture(CultureDTO cultureDTO) {
        if (!cultureRepository.existsById(cultureDTO.getId())) {
            throw new RuntimeException("Culture non trouvée");
        }
        var entityUpdate = cultureMapper.asEntity(cultureDTO);
        var updatedEntity = cultureRepository.save(entityUpdate);
        return cultureMapper.asDto(updatedEntity);
    }

    @Override
    public void deleteCulture(Long id) {
        if (!cultureRepository.existsById(id)) {
            throw new RuntimeException("Culture non trouvée");
        }
        cultureRepository.deleteById(id);
    }

    @Override
    public CultureDTO getCulture(Long id) {
        var entity = cultureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Culture non trouvée"));
        return cultureMapper.asDto(entity);
    }

    @Override
    public Page<CultureDTO> getAllCultures(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return cultureRepository.findAll(booleanBuilder, pageable)
                .map(cultureMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QCulture.culture;

            if (searchParams.containsKey("nom"))
                booleanBuilder.and(qEntity.nom.containsIgnoreCase(searchParams.get("nom")));

            if (searchParams.containsKey("categorie"))
                booleanBuilder.and(qEntity.categorie.containsIgnoreCase(searchParams.get("categorie")));

            if (searchParams.containsKey("saisonOptimale"))
                booleanBuilder.and(qEntity.saisonOptimale.containsIgnoreCase(searchParams.get("saisonOptimale")));

            if (searchParams.containsKey("dureeCroissance")) {
                Integer duree = Integer.parseInt(searchParams.get("dureeCroissance"));
                booleanBuilder.and(qEntity.dureeCroissance.eq(duree));
            }
        }
    }
}