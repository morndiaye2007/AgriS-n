package com.agri.sen.mapper;

import com.agri.sen.entity.AnalyseEntity;
import com.agri.sen.entity.AnalyseEntity.AnalyseEntityBuilder;
import com.agri.sen.entity.RapportEntity;
import com.agri.sen.entity.UtilisateurEntity;
import com.agri.sen.model.AnalyseDTO;
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
public class AnalyseMapperImpl implements AnalyseMapper {

    @Override
    public List<AnalyseDTO> parse(List<AnalyseEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<AnalyseDTO> list = new ArrayList<AnalyseDTO>( entities.size() );
        for ( AnalyseEntity analyseEntity : entities ) {
            list.add( asDto( analyseEntity ) );
        }

        return list;
    }

    @Override
    public List<AnalyseEntity> parseToEntity(List<AnalyseDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<AnalyseEntity> list = new ArrayList<AnalyseEntity>( entities.size() );
        for ( AnalyseDTO analyseDTO : entities ) {
            list.add( asEntity( analyseDTO ) );
        }

        return list;
    }

    @Override
    public AnalyseDTO asDto(AnalyseEntity entity) {
        if ( entity == null ) {
            return null;
        }

        AnalyseDTO analyseDTO = new AnalyseDTO();

        analyseDTO.setUtilisateurId( entityUtilisateurId( entity ) );
        analyseDTO.setRapportId( entityRapportId( entity ) );
        analyseDTO.setId( entity.getId() );
        analyseDTO.setMetrique( entity.getMetrique() );
        analyseDTO.setValeur( entity.getValeur() );
        analyseDTO.setPeriode( entity.getPeriode() );
        analyseDTO.setDateCreation( entity.getDateCreation() );
        analyseDTO.setUtilisateur( entity.getUtilisateur() );
        analyseDTO.setRapport( entity.getRapport() );

        return analyseDTO;
    }

    @Override
    public AnalyseEntity asEntity(AnalyseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AnalyseEntityBuilder analyseEntity = AnalyseEntity.builder();

        analyseEntity.id( dto.getId() );
        analyseEntity.metrique( dto.getMetrique() );
        analyseEntity.valeur( dto.getValeur() );
        analyseEntity.periode( dto.getPeriode() );
        analyseEntity.dateCreation( dto.getDateCreation() );

        analyseEntity.utilisateur( entityFromId(dto.getUtilisateurId(), com.agri.sen.entity.UtilisateurEntity.class) );
        analyseEntity.rapport( entityFromId(dto.getRapportId(), com.agri.sen.entity.RapportEntity.class) );

        return analyseEntity.build();
    }

    private Long entityUtilisateurId(AnalyseEntity analyseEntity) {
        if ( analyseEntity == null ) {
            return null;
        }
        UtilisateurEntity utilisateur = analyseEntity.getUtilisateur();
        if ( utilisateur == null ) {
            return null;
        }
        Long id = utilisateur.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityRapportId(AnalyseEntity analyseEntity) {
        if ( analyseEntity == null ) {
            return null;
        }
        RapportEntity rapport = analyseEntity.getRapport();
        if ( rapport == null ) {
            return null;
        }
        Long id = rapport.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
