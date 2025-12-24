package com.agri.sen.mapper;

import com.agri.sen.entity.Meteo;
import com.agri.sen.model.MeteoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MeteoMapper extends EntityMapper<MeteoDTO, Meteo>{
}
