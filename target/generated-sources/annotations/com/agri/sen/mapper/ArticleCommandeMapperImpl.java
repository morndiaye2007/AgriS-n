package com.agri.sen.mapper;

import com.agri.sen.entity.ArticleCommandeEntity;
import com.agri.sen.entity.ArticleCommandeEntity.ArticleCommandeEntityBuilder;
import com.agri.sen.entity.CommandeEntity;
import com.agri.sen.entity.ProduitEntity;
import com.agri.sen.model.ArticleCommandeDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-29T11:25:52+0000",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ArticleCommandeMapperImpl implements ArticleCommandeMapper {

    @Override
    public List<ArticleCommandeDTO> parse(List<ArticleCommandeEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ArticleCommandeDTO> list = new ArrayList<ArticleCommandeDTO>( entities.size() );
        for ( ArticleCommandeEntity articleCommandeEntity : entities ) {
            list.add( asDto( articleCommandeEntity ) );
        }

        return list;
    }

    @Override
    public List<ArticleCommandeEntity> parseToEntity(List<ArticleCommandeDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ArticleCommandeEntity> list = new ArrayList<ArticleCommandeEntity>( entities.size() );
        for ( ArticleCommandeDTO articleCommandeDTO : entities ) {
            list.add( asEntity( articleCommandeDTO ) );
        }

        return list;
    }

    @Override
    public ArticleCommandeDTO asDto(ArticleCommandeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ArticleCommandeDTO articleCommandeDTO = new ArticleCommandeDTO();

        articleCommandeDTO.setCommandeId( entityCommandeId( entity ) );
        articleCommandeDTO.setProduitId( entityProduitId( entity ) );
        articleCommandeDTO.setId( entity.getId() );
        articleCommandeDTO.setQuantite( entity.getQuantite() );
        articleCommandeDTO.setPrixUnitaire( entity.getPrixUnitaire() );
        articleCommandeDTO.setPrixTotal( entity.getPrixTotal() );
        articleCommandeDTO.setCommande( entity.getCommande() );
        articleCommandeDTO.setProduit( entity.getProduit() );

        return articleCommandeDTO;
    }

    @Override
    public ArticleCommandeEntity asEntity(ArticleCommandeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ArticleCommandeEntityBuilder articleCommandeEntity = ArticleCommandeEntity.builder();

        articleCommandeEntity.id( dto.getId() );
        articleCommandeEntity.quantite( dto.getQuantite() );
        articleCommandeEntity.prixUnitaire( dto.getPrixUnitaire() );
        articleCommandeEntity.prixTotal( dto.getPrixTotal() );

        articleCommandeEntity.commande( entityFromId(dto.getCommandeId(), com.agri.sen.entity.CommandeEntity.class) );
        articleCommandeEntity.produit( entityFromId(dto.getProduitId(), com.agri.sen.entity.ProduitEntity.class) );

        return articleCommandeEntity.build();
    }

    private Long entityCommandeId(ArticleCommandeEntity articleCommandeEntity) {
        if ( articleCommandeEntity == null ) {
            return null;
        }
        CommandeEntity commande = articleCommandeEntity.getCommande();
        if ( commande == null ) {
            return null;
        }
        Long id = commande.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityProduitId(ArticleCommandeEntity articleCommandeEntity) {
        if ( articleCommandeEntity == null ) {
            return null;
        }
        ProduitEntity produit = articleCommandeEntity.getProduit();
        if ( produit == null ) {
            return null;
        }
        Long id = produit.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
