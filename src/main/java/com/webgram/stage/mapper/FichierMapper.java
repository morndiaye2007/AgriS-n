package com.webgram.stage.mapper;

import com.webgram.stage.entity.FichierEntity;
import com.webgram.stage.model.FichierDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FichierMapper extends EntityMapper<FichierDTO, FichierEntity>{
        }