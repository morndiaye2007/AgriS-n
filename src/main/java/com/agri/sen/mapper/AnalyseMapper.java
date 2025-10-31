package com.agri.sen.mapper;

import com.agri.sen.model.AnalyseDTO;
import com.agri.sen.entity.AnalyseEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AnalyseMapper extends EntityMapper<AnalyseDTO, AnalyseEntity> {

    @Mapping(target = "utilisateurId", source = "utilisateur.id")
    @Mapping(target = "rapportId", source = "rapport.id")
    AnalyseDTO asDto(AnalyseEntity entity);

    @Mapping(target = "utilisateur", expression = "java(entityFromId(dto.getUtilisateurId(), com.agri.sen.entity.UtilisateurEntity.class))")
    @Mapping(target = "rapport", expression = "java(entityFromId(dto.getRapportId(), com.agri.sen.entity.RapportEntity.class))")
    AnalyseEntity asEntity(AnalyseDTO dto);

    default <T> T entityFromId(Long id, Class<T> type) {
        if (id == null) return null;
        try {
            T entity = type.getDeclaredConstructor().newInstance();
            type.getMethod("setId", Long.class).invoke(entity, id);
            return entity;
        } catch (Exception e) {
            return null;
        }
    }
}
