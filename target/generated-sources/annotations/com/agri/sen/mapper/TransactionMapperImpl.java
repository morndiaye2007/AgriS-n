package com.agri.sen.mapper;

import com.agri.sen.entity.PaiementEntity;
import com.agri.sen.entity.TransactionEntity;
import com.agri.sen.entity.TransactionEntity.TransactionEntityBuilder;
import com.agri.sen.model.TransactionDTO;
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
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public List<TransactionDTO> parse(List<TransactionEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<TransactionDTO> list = new ArrayList<TransactionDTO>( entities.size() );
        for ( TransactionEntity transactionEntity : entities ) {
            list.add( asDto( transactionEntity ) );
        }

        return list;
    }

    @Override
    public List<TransactionEntity> parseToEntity(List<TransactionDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<TransactionEntity> list = new ArrayList<TransactionEntity>( entities.size() );
        for ( TransactionDTO transactionDTO : entities ) {
            list.add( asEntity( transactionDTO ) );
        }

        return list;
    }

    @Override
    public TransactionDTO asDto(TransactionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionDTO transactionDTO = new TransactionDTO();

        transactionDTO.setPaiementId( entityPaiementId( entity ) );
        transactionDTO.setId( entity.getId() );
        transactionDTO.setReference( entity.getReference() );
        transactionDTO.setMontant( entity.getMontant() );
        transactionDTO.setType( entity.getType() );
        transactionDTO.setDateTransaction( entity.getDateTransaction() );
        transactionDTO.setDescription( entity.getDescription() );
        transactionDTO.setPaiement( entity.getPaiement() );

        return transactionDTO;
    }

    @Override
    public TransactionEntity asEntity(TransactionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TransactionEntityBuilder transactionEntity = TransactionEntity.builder();

        transactionEntity.id( dto.getId() );
        transactionEntity.reference( dto.getReference() );
        transactionEntity.montant( dto.getMontant() );
        transactionEntity.type( dto.getType() );
        transactionEntity.dateTransaction( dto.getDateTransaction() );
        transactionEntity.description( dto.getDescription() );

        transactionEntity.paiement( entityFromId(dto.getPaiementId(), com.agri.sen.entity.PaiementEntity.class) );

        return transactionEntity.build();
    }

    private Long entityPaiementId(TransactionEntity transactionEntity) {
        if ( transactionEntity == null ) {
            return null;
        }
        PaiementEntity paiement = transactionEntity.getPaiement();
        if ( paiement == null ) {
            return null;
        }
        Long id = paiement.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
