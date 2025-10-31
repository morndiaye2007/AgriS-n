package com.agri.sen.mapper;

import com.agri.sen.model.UtilisateurDTO;
import com.agri.sen.entity.UtilisateurEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper extends EntityMapper<UtilisateurDTO, UtilisateurEntity> {
}
