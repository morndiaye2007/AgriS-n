package com.agri.sen.mapper;

import com.agri.sen.entity.UtilisateurEntity;
import com.agri.sen.entity.UtilisateurEntity.UtilisateurEntityBuilder;
import com.agri.sen.model.UtilisateurDTO;
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
public class UtilisateurMapperImpl implements UtilisateurMapper {

    @Override
    public UtilisateurEntity asEntity(UtilisateurDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UtilisateurEntityBuilder utilisateurEntity = UtilisateurEntity.builder();

        utilisateurEntity.id( dto.getId() );
        utilisateurEntity.nom( dto.getNom() );
        utilisateurEntity.prenom( dto.getPrenom() );
        utilisateurEntity.email( dto.getEmail() );
        utilisateurEntity.motDePasse( dto.getMotDePasse() );
        utilisateurEntity.role( dto.getRole() );

        return utilisateurEntity.build();
    }

    @Override
    public UtilisateurDTO asDto(UtilisateurEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();

        utilisateurDTO.setId( entity.getId() );
        utilisateurDTO.setNom( entity.getNom() );
        utilisateurDTO.setPrenom( entity.getPrenom() );
        utilisateurDTO.setEmail( entity.getEmail() );
        utilisateurDTO.setMotDePasse( entity.getMotDePasse() );
        utilisateurDTO.setRole( entity.getRole() );

        return utilisateurDTO;
    }

    @Override
    public List<UtilisateurDTO> parse(List<UtilisateurEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UtilisateurDTO> list = new ArrayList<UtilisateurDTO>( entities.size() );
        for ( UtilisateurEntity utilisateurEntity : entities ) {
            list.add( asDto( utilisateurEntity ) );
        }

        return list;
    }

    @Override
    public List<UtilisateurEntity> parseToEntity(List<UtilisateurDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UtilisateurEntity> list = new ArrayList<UtilisateurEntity>( entities.size() );
        for ( UtilisateurDTO utilisateurDTO : entities ) {
            list.add( asEntity( utilisateurDTO ) );
        }

        return list;
    }
}
