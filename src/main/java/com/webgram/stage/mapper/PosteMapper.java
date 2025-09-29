package com.webgram.stage.mapper;

import com.webgram.stage.entity.PosteEntity;
import com.webgram.stage.entity.PaysEntity;
import com.webgram.stage.model.PosteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PosteMapper extends EntityMapper<PosteDTO, PosteEntity> {

}

