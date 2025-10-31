package com.agri.sen.mapper;

import com.agri.sen.model.PaiementDTO;
import com.agri.sen.entity.PaiementEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PaiementMapper extends EntityMapper<PaiementDTO, PaiementEntity> {

    @Mapping(target = "commandeId", source = "commande.id")
    PaiementDTO asDto(PaiementEntity entity);

    @Mapping(target = "commande", expression = "java(entityFromId(dto.getCommandeId(), com.agri.sen.entity.CommandeEntity.class))")
    PaiementEntity asEntity(PaiementDTO dto);

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
