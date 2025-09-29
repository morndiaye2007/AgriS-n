package com.webgram.stage.mapper;

import com.webgram.stage.entity.AgentEntity;
import com.webgram.stage.entity.ProjetEntity;
import com.webgram.stage.entity.PaysEntity;
import com.webgram.stage.model.ProjetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjetMapper extends EntityMapper<ProjetDTO, ProjetEntity> {

    @Mapping(target = "agent", source = "agentId", qualifiedByName = "mapAgent")
    ProjetEntity asEntity(ProjetDTO dto);

    @Named("mapAgent")
    default AgentEntity mapAgent(Long agentId) {
        if (agentId == null) return null;
        AgentEntity agent = new AgentEntity();
        agent.setId(agentId);
        return agent;
    }


}
