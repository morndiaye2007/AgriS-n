package com.webgram.stage.mapper;

import com.webgram.stage.entity.AgentEntity;
import com.webgram.stage.entity.PaysEntity;
import com.webgram.stage.model.AgentDTO;
import com.webgram.stage.model.PaysDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PaysMapper extends EntityMapper<PaysDTO, PaysEntity> {
}
