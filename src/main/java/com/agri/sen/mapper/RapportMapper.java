package com.agri.sen.mapper;

import com.agri.sen.model.RapportDTO;
import com.agri.sen.entity.RapportEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RapportMapper extends EntityMapper<RapportDTO, RapportEntity> {
}
