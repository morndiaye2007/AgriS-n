package com.agri.sen.mapper;

import com.agri.sen.entity.Parcelle;
import com.agri.sen.model.ParcelleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ParcelleMapper extends EntityMapper<ParcelleDTO, Parcelle>{
}
