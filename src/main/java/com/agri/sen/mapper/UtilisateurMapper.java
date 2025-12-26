package com.agri.sen.mapper;

import com.agri.sen.entity.Utilisateur;
import com.agri.sen.model.UtilisateurDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UtilisateurMapper extends EntityMapper<UtilisateurDTO, Utilisateur>{
}
