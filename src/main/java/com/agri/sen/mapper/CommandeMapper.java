package com.agri.sen.mapper;

import com.agri.sen.entity.CommandeEntity;
import com.agri.sen.model.CommandeDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CommandeMapper extends EntityMapper<CommandeDTO, CommandeEntity> {

    @Mapping(target = "acheteurId", source = "acheteur.id")
    CommandeDTO asDto(CommandeEntity entity);

    @Mapping(target = "acheteur", expression = "java(entityFromId(dto.getAcheteurId(), com.agri.sen.entity.UtilisateurEntity.class))")
    CommandeEntity asEntity(CommandeDTO dto);

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
