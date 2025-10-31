package com.agri.sen.mapper;

import com.agri.sen.model.RecommandationDTO;
import com.agri.sen.entity.RecommandationEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RecommandationMapper extends EntityMapper<RecommandationDTO, RecommandationEntity> {

    @Mapping(target = "ressourceId", source = "ressource.id")
    RecommandationDTO asDto(RecommandationEntity entity);

    @Mapping(target = "ressource", expression = "java(entityFromId(dto.getRessourceId(), com.agri.sen.entity.RessourceEntity.class))")
    RecommandationEntity asEntity(RecommandationDTO dto);

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
