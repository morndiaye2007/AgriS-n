package com.agri.sen.mapper;

import com.agri.sen.entity.Utilisateur;
import com.agri.sen.entity.Utilisateur.UtilisateurBuilder;
import com.agri.sen.model.UtilisateurDTO;
import com.agri.sen.model.UtilisateurDTO.UtilisateurDTOBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-24T21:46:57+0000",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class UtilisateurMapperImpl implements UtilisateurMapper {

    @Override
    public Utilisateur asEntity(UtilisateurDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UtilisateurBuilder utilisateur = Utilisateur.builder();

        utilisateur.id( dto.getId() );
        utilisateur.email( dto.getEmail() );
        utilisateur.nom( dto.getNom() );
        utilisateur.prenom( dto.getPrenom() );
        utilisateur.telephone( dto.getTelephone() );
        utilisateur.adresse( dto.getAdresse() );
        utilisateur.ville( dto.getVille() );
        utilisateur.codePostal( dto.getCodePostal() );
        utilisateur.profileImage( dto.getProfileImage() );
        utilisateur.role( dto.getRole() );
        utilisateur.enabled( dto.getEnabled() );
        utilisateur.lastLogin( dto.getLastLogin() );
        utilisateur.createdAt( dto.getCreatedAt() );
        utilisateur.updatedAt( dto.getUpdatedAt() );

        return utilisateur.build();
    }

    @Override
    public UtilisateurDTO asDto(Utilisateur entity) {
        if ( entity == null ) {
            return null;
        }

        UtilisateurDTOBuilder utilisateurDTO = UtilisateurDTO.builder();

        utilisateurDTO.id( entity.getId() );
        utilisateurDTO.email( entity.getEmail() );
        utilisateurDTO.nom( entity.getNom() );
        utilisateurDTO.prenom( entity.getPrenom() );
        utilisateurDTO.telephone( entity.getTelephone() );
        utilisateurDTO.adresse( entity.getAdresse() );
        utilisateurDTO.ville( entity.getVille() );
        utilisateurDTO.codePostal( entity.getCodePostal() );
        utilisateurDTO.profileImage( entity.getProfileImage() );
        utilisateurDTO.role( entity.getRole() );
        utilisateurDTO.enabled( entity.getEnabled() );
        utilisateurDTO.lastLogin( entity.getLastLogin() );
        utilisateurDTO.createdAt( entity.getCreatedAt() );
        utilisateurDTO.updatedAt( entity.getUpdatedAt() );

        return utilisateurDTO.build();
    }

    @Override
    public List<UtilisateurDTO> parse(List<Utilisateur> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UtilisateurDTO> list = new ArrayList<UtilisateurDTO>( entities.size() );
        for ( Utilisateur utilisateur : entities ) {
            list.add( asDto( utilisateur ) );
        }

        return list;
    }

    @Override
    public List<Utilisateur> parseToEntity(List<UtilisateurDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Utilisateur> list = new ArrayList<Utilisateur>( entities.size() );
        for ( UtilisateurDTO utilisateurDTO : entities ) {
            list.add( asEntity( utilisateurDTO ) );
        }

        return list;
    }
}
