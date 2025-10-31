package com.agri.sen.mapper;

import com.agri.sen.model.RessourceDTO;
import com.agri.sen.entity.RessourceEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RessourceMapper extends EntityMapper<RessourceDTO, RessourceEntity> {

    @Mapping(target = "auteurId", source = "auteur.id")
    RessourceDTO asDto(RessourceEntity entity);

    @Mapping(target = "auteur", expression = "java(entityFromId(dto.getAuteurId(), com.agri.sen.entity.UtilisateurEntity.class))")
    RessourceEntity asEntity(RessourceDTO dto);

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
