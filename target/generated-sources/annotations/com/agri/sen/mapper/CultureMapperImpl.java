package com.agri.sen.mapper;

import com.agri.sen.entity.Culture;
import com.agri.sen.entity.Culture.CultureBuilder;
import com.agri.sen.model.CultureDTO;
import com.agri.sen.model.CultureDTO.CultureDTOBuilder;
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
public class CultureMapperImpl implements CultureMapper {

    @Override
    public Culture asEntity(CultureDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CultureBuilder culture = Culture.builder();

        culture.id( dto.getId() );
        culture.nom( dto.getNom() );
        culture.description( dto.getDescription() );
        culture.categorie( dto.getCategorie() );
        culture.dureeCroissance( dto.getDureeCroissance() );
        culture.saisonOptimale( dto.getSaisonOptimale() );
        culture.imageUrl( dto.getImageUrl() );

        return culture.build();
    }

    @Override
    public CultureDTO asDto(Culture entity) {
        if ( entity == null ) {
            return null;
        }

        CultureDTOBuilder cultureDTO = CultureDTO.builder();

        cultureDTO.id( entity.getId() );
        cultureDTO.nom( entity.getNom() );
        cultureDTO.description( entity.getDescription() );
        cultureDTO.categorie( entity.getCategorie() );
        cultureDTO.dureeCroissance( entity.getDureeCroissance() );
        cultureDTO.saisonOptimale( entity.getSaisonOptimale() );
        cultureDTO.imageUrl( entity.getImageUrl() );

        return cultureDTO.build();
    }

    @Override
    public List<CultureDTO> parse(List<Culture> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CultureDTO> list = new ArrayList<CultureDTO>( entities.size() );
        for ( Culture culture : entities ) {
            list.add( asDto( culture ) );
        }

        return list;
    }

    @Override
    public List<Culture> parseToEntity(List<CultureDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Culture> list = new ArrayList<Culture>( entities.size() );
        for ( CultureDTO cultureDTO : entities ) {
            list.add( asEntity( cultureDTO ) );
        }

        return list;
    }
}
