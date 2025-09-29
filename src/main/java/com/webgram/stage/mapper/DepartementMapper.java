package com.webgram.stage.mapper;

import com.webgram.stage.entity.AgentEntity;
import com.webgram.stage.entity.DepartementEntity;
import com.webgram.stage.entity.FormationEntity;
import com.webgram.stage.entity.PaysEntity;
import com.webgram.stage.model.DepartementDTO;
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
public interface DepartementMapper extends EntityMapper<DepartementDTO, DepartementEntity> {

    @Mapping(target = "agent", source = "agentId", qualifiedByName = "mapAgent")
    DepartementEntity asEntity(DepartementDTO dto);

    @Named("mapAgent")
    default AgentEntity mapAgent(Long id) {
        if (Objects.nonNull(id)) {
            return AgentEntity.builder().id(id).build();
        }
        return null;
    }

}
