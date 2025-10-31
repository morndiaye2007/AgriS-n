package com.agri.sen.mapper;

import com.agri.sen.model.ProduitDTO;
import com.agri.sen.entity.ProduitEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProduitMapper extends EntityMapper<ProduitDTO, ProduitEntity> {

    @Mapping(target = "categorieId", source = "categorie.id")
    @Mapping(target = "vendeurId", source = "vendeur.id")
    ProduitDTO asDto(ProduitEntity entity);

    @Mapping(target = "categorie", expression = "java(entityFromId(dto.getCategorieId(), com.agri.sen.entity.CategorieEntity.class))")
    @Mapping(target = "vendeur", expression = "java(entityFromId(dto.getVendeurId(), com.agri.sen.entity.UtilisateurEntity.class))")
    ProduitEntity asEntity(ProduitDTO dto);

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
