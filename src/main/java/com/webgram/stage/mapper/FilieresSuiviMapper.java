package com.webgram.stage.mapper;

import com.webgram.stage.entity.FilieresSuiviEntity;
import com.webgram.stage.model.FilieresSuiviDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FilieresSuiviMapper extends EntityMapper<FilieresSuiviDTO, FilieresSuiviEntity> {
}
