package com.agri.sen.mapper;

import com.agri.sen.entity.CategorieEntity;
import com.agri.sen.model.CategorieDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategorieMapper extends EntityMapper<CategorieDTO, CategorieEntity> {
}
