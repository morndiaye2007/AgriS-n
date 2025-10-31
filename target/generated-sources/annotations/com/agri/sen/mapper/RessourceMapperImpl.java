package com.agri.sen.mapper;

import com.agri.sen.entity.RessourceEntity;
import com.agri.sen.entity.RessourceEntity.RessourceEntityBuilder;
import com.agri.sen.entity.UtilisateurEntity;
import com.agri.sen.model.RessourceDTO;
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
public class RessourceMapperImpl implements RessourceMapper {

    @Override
    public List<RessourceDTO> parse(List<RessourceEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<RessourceDTO> list = new ArrayList<RessourceDTO>( entities.size() );
        for ( RessourceEntity ressourceEntity : entities ) {
            list.add( asDto( ressourceEntity ) );
        }

        return list;
    }

    @Override
    public List<RessourceEntity> parseToEntity(List<RessourceDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<RessourceEntity> list = new ArrayList<RessourceEntity>( entities.size() );
        for ( RessourceDTO ressourceDTO : entities ) {
            list.add( asEntity( ressourceDTO ) );
        }

        return list;
    }

    @Override
    public RessourceDTO asDto(RessourceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        RessourceDTO ressourceDTO = new RessourceDTO();

        ressourceDTO.setAuteurId( entityAuteurId( entity ) );
        ressourceDTO.setId( entity.getId() );
        ressourceDTO.setTitre( entity.getTitre() );
        ressourceDTO.setDescription( entity.getDescription() );
        ressourceDTO.setType( entity.getType() );
        ressourceDTO.setUrl( entity.getUrl() );
        ressourceDTO.setRegion( entity.getRegion() );
        ressourceDTO.setLangue( entity.getLangue() );
        ressourceDTO.setDateCreation( entity.getDateCreation() );
        ressourceDTO.setAuteur( entity.getAuteur() );

        return ressourceDTO;
    }

    @Override
    public RessourceEntity asEntity(RessourceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RessourceEntityBuilder ressourceEntity = RessourceEntity.builder();

        ressourceEntity.id( dto.getId() );
        ressourceEntity.titre( dto.getTitre() );
        ressourceEntity.description( dto.getDescription() );
        ressourceEntity.type( dto.getType() );
        ressourceEntity.url( dto.getUrl() );
        ressourceEntity.region( dto.getRegion() );
        ressourceEntity.langue( dto.getLangue() );
        ressourceEntity.dateCreation( dto.getDateCreation() );

        ressourceEntity.auteur( entityFromId(dto.getAuteurId(), com.agri.sen.entity.UtilisateurEntity.class) );

        return ressourceEntity.build();
    }

    private Long entityAuteurId(RessourceEntity ressourceEntity) {
        if ( ressourceEntity == null ) {
            return null;
        }
        UtilisateurEntity auteur = ressourceEntity.getAuteur();
        if ( auteur == null ) {
            return null;
        }
        Long id = auteur.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
