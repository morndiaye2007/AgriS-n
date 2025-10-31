package com.agri.sen.mapper;

import com.agri.sen.entity.CommandeEntity;
import com.agri.sen.entity.PaiementEntity;
import com.agri.sen.entity.PaiementEntity.PaiementEntityBuilder;
import com.agri.sen.model.PaiementDTO;
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
public class PaiementMapperImpl implements PaiementMapper {

    @Override
    public List<PaiementDTO> parse(List<PaiementEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PaiementDTO> list = new ArrayList<PaiementDTO>( entities.size() );
        for ( PaiementEntity paiementEntity : entities ) {
            list.add( asDto( paiementEntity ) );
        }

        return list;
    }

    @Override
    public List<PaiementEntity> parseToEntity(List<PaiementDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PaiementEntity> list = new ArrayList<PaiementEntity>( entities.size() );
        for ( PaiementDTO paiementDTO : entities ) {
            list.add( asEntity( paiementDTO ) );
        }

        return list;
    }

    @Override
    public PaiementDTO asDto(PaiementEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PaiementDTO paiementDTO = new PaiementDTO();

        paiementDTO.setCommandeId( entityCommandeId( entity ) );
        paiementDTO.setId( entity.getId() );
        paiementDTO.setMoyenPaiement( entity.getMoyenPaiement() );
        paiementDTO.setMontant( entity.getMontant() );
        paiementDTO.setDatePaiement( entity.getDatePaiement() );
        paiementDTO.setStatut( entity.getStatut() );
        paiementDTO.setReferenceTransaction( entity.getReferenceTransaction() );
        paiementDTO.setCommande( entity.getCommande() );

        return paiementDTO;
    }

    @Override
    public PaiementEntity asEntity(PaiementDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PaiementEntityBuilder paiementEntity = PaiementEntity.builder();

        paiementEntity.id( dto.getId() );
        paiementEntity.moyenPaiement( dto.getMoyenPaiement() );
        paiementEntity.montant( dto.getMontant() );
        paiementEntity.datePaiement( dto.getDatePaiement() );
        paiementEntity.statut( dto.getStatut() );
        paiementEntity.referenceTransaction( dto.getReferenceTransaction() );

        paiementEntity.commande( entityFromId(dto.getCommandeId(), com.agri.sen.entity.CommandeEntity.class) );

        return paiementEntity.build();
    }

    private Long entityCommandeId(PaiementEntity paiementEntity) {
        if ( paiementEntity == null ) {
            return null;
        }
        CommandeEntity commande = paiementEntity.getCommande();
        if ( commande == null ) {
            return null;
        }
        Long id = commande.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
