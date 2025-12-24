package com.agri.sen.mapper;

import com.agri.sen.entity.Parcelle;
import com.agri.sen.entity.Parcelle.ParcelleBuilder;
import com.agri.sen.model.ParcelleDTO;
import com.agri.sen.model.ParcelleDTO.ParcelleDTOBuilder;
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
public class ParcelleMapperImpl implements ParcelleMapper {

    @Override
    public Parcelle asEntity(ParcelleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ParcelleBuilder parcelle = Parcelle.builder();

        parcelle.id( dto.getId() );
        parcelle.nom( dto.getNom() );
        parcelle.agriculteurId( dto.getAgriculteurId() );
        parcelle.cultureId( dto.getCultureId() );

        return parcelle.build();
    }

    @Override
    public ParcelleDTO asDto(Parcelle entity) {
        if ( entity == null ) {
            return null;
        }

        ParcelleDTOBuilder parcelleDTO = ParcelleDTO.builder();

        parcelleDTO.id( entity.getId() );
        parcelleDTO.nom( entity.getNom() );
        parcelleDTO.cultureId( entity.getCultureId() );

        return parcelleDTO.build();
    }

    @Override
    public List<ParcelleDTO> parse(List<Parcelle> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ParcelleDTO> list = new ArrayList<ParcelleDTO>( entities.size() );
        for ( Parcelle parcelle : entities ) {
            list.add( asDto( parcelle ) );
        }

        return list;
    }

    @Override
    public List<Parcelle> parseToEntity(List<ParcelleDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Parcelle> list = new ArrayList<Parcelle>( entities.size() );
        for ( ParcelleDTO parcelleDTO : entities ) {
            list.add( asEntity( parcelleDTO ) );
        }

        return list;
    }
}
