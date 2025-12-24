package com.agri.sen.mapper;

import com.agri.sen.entity.Culture;
import com.agri.sen.model.CultureDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CultureMapper extends EntityMapper<CultureDTO, Culture>{

}