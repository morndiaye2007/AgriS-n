package com.agri.sen.mapper;

import com.agri.sen.entity.Meteo;
import com.agri.sen.entity.Meteo.MeteoBuilder;
import com.agri.sen.model.MeteoDTO;
import com.agri.sen.model.MeteoDTO.MeteoDTOBuilder;
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
public class MeteoMapperImpl implements MeteoMapper {

    @Override
    public Meteo asEntity(MeteoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MeteoBuilder meteo = Meteo.builder();

        meteo.id( dto.getId() );
        meteo.latitude( dto.getLatitude() );
        meteo.longitude( dto.getLongitude() );
        meteo.ville( dto.getVille() );
        meteo.temperature( dto.getTemperature() );
        meteo.temperatureMin( dto.getTemperatureMin() );
        meteo.temperatureMax( dto.getTemperatureMax() );
        meteo.humidite( dto.getHumidite() );
        meteo.vitesseVent( dto.getVitesseVent() );
        meteo.description( dto.getDescription() );
        meteo.icone( dto.getIcone() );
        meteo.probabilitePluie( dto.getProbabilitePluie() );
        meteo.datePrevisionnelle( dto.getDatePrevisionnelle() );
        meteo.createdAt( dto.getCreatedAt() );

        return meteo.build();
    }

    @Override
    public MeteoDTO asDto(Meteo entity) {
        if ( entity == null ) {
            return null;
        }

        MeteoDTOBuilder meteoDTO = MeteoDTO.builder();

        meteoDTO.id( entity.getId() );
        meteoDTO.latitude( entity.getLatitude() );
        meteoDTO.longitude( entity.getLongitude() );
        meteoDTO.ville( entity.getVille() );
        meteoDTO.temperature( entity.getTemperature() );
        meteoDTO.temperatureMin( entity.getTemperatureMin() );
        meteoDTO.temperatureMax( entity.getTemperatureMax() );
        meteoDTO.humidite( entity.getHumidite() );
        meteoDTO.vitesseVent( entity.getVitesseVent() );
        meteoDTO.description( entity.getDescription() );
        meteoDTO.icone( entity.getIcone() );
        meteoDTO.probabilitePluie( entity.getProbabilitePluie() );
        meteoDTO.datePrevisionnelle( entity.getDatePrevisionnelle() );
        meteoDTO.createdAt( entity.getCreatedAt() );

        return meteoDTO.build();
    }

    @Override
    public List<MeteoDTO> parse(List<Meteo> entities) {
        if ( entities == null ) {
            return null;
        }

        List<MeteoDTO> list = new ArrayList<MeteoDTO>( entities.size() );
        for ( Meteo meteo : entities ) {
            list.add( asDto( meteo ) );
        }

        return list;
    }

    @Override
    public List<Meteo> parseToEntity(List<MeteoDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Meteo> list = new ArrayList<Meteo>( entities.size() );
        for ( MeteoDTO meteoDTO : entities ) {
            list.add( asEntity( meteoDTO ) );
        }

        return list;
    }
}
