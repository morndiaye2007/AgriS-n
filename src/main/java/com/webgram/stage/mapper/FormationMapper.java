package com.webgram.stage.mapper;

import com.webgram.stage.entity.FormationEntity;
import com.webgram.stage.entity.PaysEntity;
import com.webgram.stage.model.FormationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FormationMapper extends EntityMapper<FormationDTO, FormationEntity> {


}
