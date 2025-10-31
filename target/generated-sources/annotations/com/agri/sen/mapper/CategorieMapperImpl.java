package com.agri.sen.mapper;

import com.agri.sen.entity.CategorieEntity;
import com.agri.sen.entity.CategorieEntity.CategorieEntityBuilder;
import com.agri.sen.model.CategorieDTO;
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
public class CategorieMapperImpl implements CategorieMapper {

    @Override
    public CategorieEntity asEntity(CategorieDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CategorieEntityBuilder categorieEntity = CategorieEntity.builder();

        categorieEntity.id( dto.getId() );
        categorieEntity.nom( dto.getNom() );
        categorieEntity.description( dto.getDescription() );

        return categorieEntity.build();
    }

    @Override
    public CategorieDTO asDto(CategorieEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CategorieDTO categorieDTO = new CategorieDTO();

        categorieDTO.setId( entity.getId() );
        categorieDTO.setNom( entity.getNom() );
        categorieDTO.setDescription( entity.getDescription() );

        return categorieDTO;
    }

    @Override
    public List<CategorieDTO> parse(List<CategorieEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CategorieDTO> list = new ArrayList<CategorieDTO>( entities.size() );
        for ( CategorieEntity categorieEntity : entities ) {
            list.add( asDto( categorieEntity ) );
        }

        return list;
    }

    @Override
    public List<CategorieEntity> parseToEntity(List<CategorieDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CategorieEntity> list = new ArrayList<CategorieEntity>( entities.size() );
        for ( CategorieDTO categorieDTO : entities ) {
            list.add( asEntity( categorieDTO ) );
        }

        return list;
    }
}
