package com.agri.sen.mapper;

import com.agri.sen.entity.RapportEntity;
import com.agri.sen.entity.RapportEntity.RapportEntityBuilder;
import com.agri.sen.model.RapportDTO;
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
public class RapportMapperImpl implements RapportMapper {

    @Override
    public RapportEntity asEntity(RapportDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RapportEntityBuilder rapportEntity = RapportEntity.builder();

        rapportEntity.id( dto.getId() );
        rapportEntity.titre( dto.getTitre() );
        rapportEntity.type( dto.getType() );
        rapportEntity.donnees( dto.getDonnees() );
        rapportEntity.dateGeneration( dto.getDateGeneration() );

        return rapportEntity.build();
    }

    @Override
    public RapportDTO asDto(RapportEntity entity) {
        if ( entity == null ) {
            return null;
        }

        RapportDTO rapportDTO = new RapportDTO();

        rapportDTO.setId( entity.getId() );
        rapportDTO.setTitre( entity.getTitre() );
        rapportDTO.setType( entity.getType() );
        rapportDTO.setDonnees( entity.getDonnees() );
        rapportDTO.setDateGeneration( entity.getDateGeneration() );

        return rapportDTO;
    }

    @Override
    public List<RapportDTO> parse(List<RapportEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<RapportDTO> list = new ArrayList<RapportDTO>( entities.size() );
        for ( RapportEntity rapportEntity : entities ) {
            list.add( asDto( rapportEntity ) );
        }

        return list;
    }

    @Override
    public List<RapportEntity> parseToEntity(List<RapportDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<RapportEntity> list = new ArrayList<RapportEntity>( entities.size() );
        for ( RapportDTO rapportDTO : entities ) {
            list.add( asEntity( rapportDTO ) );
        }

        return list;
    }
}
