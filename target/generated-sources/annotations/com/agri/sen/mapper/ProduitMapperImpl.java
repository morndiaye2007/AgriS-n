package com.agri.sen.mapper;

import com.agri.sen.entity.CategorieEntity;
import com.agri.sen.entity.ProduitEntity;
import com.agri.sen.entity.ProduitEntity.ProduitEntityBuilder;
import com.agri.sen.entity.UtilisateurEntity;
import com.agri.sen.model.ProduitDTO;
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
public class ProduitMapperImpl implements ProduitMapper {

    @Override
    public List<ProduitDTO> parse(List<ProduitEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProduitDTO> list = new ArrayList<ProduitDTO>( entities.size() );
        for ( ProduitEntity produitEntity : entities ) {
            list.add( asDto( produitEntity ) );
        }

        return list;
    }

    @Override
    public List<ProduitEntity> parseToEntity(List<ProduitDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProduitEntity> list = new ArrayList<ProduitEntity>( entities.size() );
        for ( ProduitDTO produitDTO : entities ) {
            list.add( asEntity( produitDTO ) );
        }

        return list;
    }

    @Override
    public ProduitDTO asDto(ProduitEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProduitDTO produitDTO = new ProduitDTO();

        produitDTO.setCategorieId( entityCategorieId( entity ) );
        produitDTO.setVendeurId( entityVendeurId( entity ) );
        produitDTO.setId( entity.getId() );
        produitDTO.setNom( entity.getNom() );
        produitDTO.setDescription( entity.getDescription() );
        produitDTO.setPrix( entity.getPrix() );
        produitDTO.setStock( entity.getStock() );
        produitDTO.setImageUrl( entity.getImageUrl() );
        produitDTO.setDisponible( entity.getDisponible() );
        produitDTO.setDateCreation( entity.getDateCreation() );
        produitDTO.setDateModification( entity.getDateModification() );
        produitDTO.setCategorie( entity.getCategorie() );
        produitDTO.setVendeur( entity.getVendeur() );

        return produitDTO;
    }

    @Override
    public ProduitEntity asEntity(ProduitDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ProduitEntityBuilder produitEntity = ProduitEntity.builder();

        produitEntity.id( dto.getId() );
        produitEntity.nom( dto.getNom() );
        produitEntity.description( dto.getDescription() );
        produitEntity.prix( dto.getPrix() );
        produitEntity.stock( dto.getStock() );
        produitEntity.imageUrl( dto.getImageUrl() );
        produitEntity.disponible( dto.getDisponible() );
        produitEntity.dateCreation( dto.getDateCreation() );
        produitEntity.dateModification( dto.getDateModification() );

        produitEntity.categorie( entityFromId(dto.getCategorieId(), com.agri.sen.entity.CategorieEntity.class) );
        produitEntity.vendeur( entityFromId(dto.getVendeurId(), com.agri.sen.entity.UtilisateurEntity.class) );

        return produitEntity.build();
    }

    private Long entityCategorieId(ProduitEntity produitEntity) {
        if ( produitEntity == null ) {
            return null;
        }
        CategorieEntity categorie = produitEntity.getCategorie();
        if ( categorie == null ) {
            return null;
        }
        Long id = categorie.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityVendeurId(ProduitEntity produitEntity) {
        if ( produitEntity == null ) {
            return null;
        }
        UtilisateurEntity vendeur = produitEntity.getVendeur();
        if ( vendeur == null ) {
            return null;
        }
        Long id = vendeur.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
