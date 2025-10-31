package com.agri.sen.mapper;

import com.agri.sen.entity.ArticleCommandeEntity;
import com.agri.sen.model.ArticleCommandeDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ArticleCommandeMapper extends EntityMapper<ArticleCommandeDTO, ArticleCommandeEntity> {

    @Mapping(target = "commandeId", source = "commande.id")
    @Mapping(target = "produitId", source = "produit.id")
    ArticleCommandeDTO asDto(ArticleCommandeEntity entity);

    @Mapping(target = "commande", expression = "java(entityFromId(dto.getCommandeId(), com.agri.sen.entity.CommandeEntity.class))")
    @Mapping(target = "produit", expression = "java(entityFromId(dto.getProduitId(), com.agri.sen.entity.ProduitEntity.class))")
    ArticleCommandeEntity asEntity(ArticleCommandeDTO dto);

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
