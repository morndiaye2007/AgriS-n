package com.agri.sen.mapper;

import com.agri.sen.model.TransactionDTO;
import com.agri.sen.entity.TransactionEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends EntityMapper<TransactionDTO, TransactionEntity> {

    @Mapping(target = "paiementId", source = "paiement.id")
    TransactionDTO asDto(TransactionEntity entity);

    @Mapping(target = "paiement", expression = "java(entityFromId(dto.getPaiementId(), com.agri.sen.entity.PaiementEntity.class))")
    TransactionEntity asEntity(TransactionDTO dto);

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
