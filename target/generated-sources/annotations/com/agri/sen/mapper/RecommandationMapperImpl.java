package com.agri.sen.mapper;

import com.agri.sen.entity.RecommandationEntity;
import com.agri.sen.entity.RecommandationEntity.RecommandationEntityBuilder;
import com.agri.sen.entity.RessourceEntity;
import com.agri.sen.model.RecommandationDTO;
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
public class RecommandationMapperImpl implements RecommandationMapper {

    @Override
    public List<RecommandationDTO> parse(List<RecommandationEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<RecommandationDTO> list = new ArrayList<RecommandationDTO>( entities.size() );
        for ( RecommandationEntity recommandationEntity : entities ) {
            list.add( asDto( recommandationEntity ) );
        }

        return list;
    }

    @Override
    public List<RecommandationEntity> parseToEntity(List<RecommandationDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<RecommandationEntity> list = new ArrayList<RecommandationEntity>( entities.size() );
        for ( RecommandationDTO recommandationDTO : entities ) {
            list.add( asEntity( recommandationDTO ) );
        }

        return list;
    }

    @Override
    public RecommandationDTO asDto(RecommandationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        RecommandationDTO recommandationDTO = new RecommandationDTO();

        recommandationDTO.setRessourceId( entityRessourceId( entity ) );
        recommandationDTO.setId( entity.getId() );
        recommandationDTO.setRegion( entity.getRegion() );
        recommandationDTO.setSaison( entity.getSaison() );
        recommandationDTO.setCulturesRecommandees( entity.getCulturesRecommandees() );
        recommandationDTO.setDescription( entity.getDescription() );
        recommandationDTO.setRessource( entity.getRessource() );

        return recommandationDTO;
    }

    @Override
    public RecommandationEntity asEntity(RecommandationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RecommandationEntityBuilder recommandationEntity = RecommandationEntity.builder();

        recommandationEntity.id( dto.getId() );
        recommandationEntity.region( dto.getRegion() );
        recommandationEntity.saison( dto.getSaison() );
        recommandationEntity.culturesRecommandees( dto.getCulturesRecommandees() );
        recommandationEntity.description( dto.getDescription() );

        recommandationEntity.ressource( entityFromId(dto.getRessourceId(), com.agri.sen.entity.RessourceEntity.class) );

        return recommandationEntity.build();
    }

    private Long entityRessourceId(RecommandationEntity recommandationEntity) {
        if ( recommandationEntity == null ) {
            return null;
        }
        RessourceEntity ressource = recommandationEntity.getRessource();
        if ( ressource == null ) {
            return null;
        }
        Long id = ressource.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
