package com.agri.sen.mapper;

import com.agri.sen.entity.CommandeEntity;
import com.agri.sen.entity.CommandeEntity.CommandeEntityBuilder;
import com.agri.sen.entity.UtilisateurEntity;
import com.agri.sen.model.CommandeDTO;
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
public class CommandeMapperImpl implements CommandeMapper {

    @Override
    public List<CommandeDTO> parse(List<CommandeEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CommandeDTO> list = new ArrayList<CommandeDTO>( entities.size() );
        for ( CommandeEntity commandeEntity : entities ) {
            list.add( asDto( commandeEntity ) );
        }

        return list;
    }

    @Override
    public List<CommandeEntity> parseToEntity(List<CommandeDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CommandeEntity> list = new ArrayList<CommandeEntity>( entities.size() );
        for ( CommandeDTO commandeDTO : entities ) {
            list.add( asEntity( commandeDTO ) );
        }

        return list;
    }

    @Override
    public CommandeDTO asDto(CommandeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CommandeDTO commandeDTO = new CommandeDTO();

        commandeDTO.setAcheteurId( entityAcheteurId( entity ) );
        commandeDTO.setId( entity.getId() );
        commandeDTO.setNumeroCommande( entity.getNumeroCommande() );
        commandeDTO.setStatut( entity.getStatut() );
        commandeDTO.setMontantTotal( entity.getMontantTotal() );
        commandeDTO.setDateCreation( entity.getDateCreation() );
        commandeDTO.setDateModification( entity.getDateModification() );
        commandeDTO.setAcheteur( entity.getAcheteur() );
        commandeDTO.setPaiement( entity.getPaiement() );

        return commandeDTO;
    }

    @Override
    public CommandeEntity asEntity(CommandeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CommandeEntityBuilder commandeEntity = CommandeEntity.builder();

        commandeEntity.id( dto.getId() );
        commandeEntity.numeroCommande( dto.getNumeroCommande() );
        commandeEntity.statut( dto.getStatut() );
        commandeEntity.montantTotal( dto.getMontantTotal() );
        commandeEntity.dateCreation( dto.getDateCreation() );
        commandeEntity.dateModification( dto.getDateModification() );
        commandeEntity.paiement( dto.getPaiement() );

        commandeEntity.acheteur( entityFromId(dto.getAcheteurId(), com.agri.sen.entity.UtilisateurEntity.class) );

        return commandeEntity.build();
    }

    private Long entityAcheteurId(CommandeEntity commandeEntity) {
        if ( commandeEntity == null ) {
            return null;
        }
        UtilisateurEntity acheteur = commandeEntity.getAcheteur();
        if ( acheteur == null ) {
            return null;
        }
        Long id = acheteur.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
