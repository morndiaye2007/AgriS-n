package com.webgram.stage.mapper;

import com.webgram.stage.entity.LangueEntity;
import com.webgram.stage.entity.PaysEntity;
import com.webgram.stage.model.LangueDTO;
import com.webgram.stage.model.PaysDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LangueMapper extends EntityMapper<LangueDTO, LangueEntity> {
}
