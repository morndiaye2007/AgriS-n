package com.webgram.stage.mapper;

import com.webgram.stage.entity.AgentEntity;
import com.webgram.stage.entity.PaysEntity;
import com.webgram.stage.model.AgentDTO;
import com.webgram.stage.model.AgentExcelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AgentMapper extends EntityMapper<AgentDTO, AgentEntity> {

    @Mapping(target = "pays", source = "paysId", qualifiedByName = "getPays")
    AgentEntity asEntity(AgentDTO dto);

    @Named("getPays")
    default PaysEntity getPays(Long id) {
        if (Objects.nonNull(id)) {
            return PaysEntity.builder().id(id).build();
        }
        return null;
    }

    @Mapping(target = "pays", source = "pays.libelle")
    @Mapping(target = "langue", source = "langue.libelle")
    @Mapping(target = "poste", source = "poste.intitule")
    @Mapping(target = "filiereSuivi", source = "filiereSuivi.libelle")
    @Mapping(target = "dateNaissance", source = "dateNaissance", qualifiedByName = "formatDate")
    @Mapping(target = "sexe", source = "sexe", qualifiedByName = "formatSexe")
    @Mapping(target = "typeContrat", source = "typeContrat", qualifiedByName = "formatTypeContrat")
    @Mapping(target = "statutAgent", source = "statutAgent", qualifiedByName = "formatStatutAgent")
    @Mapping(target = "preferences", source = "preferences", qualifiedByName = "formatPreferences")
    AgentExcelDTO asExcelDto(AgentEntity entity);

    @Named("formatDate")
    default String formatDate(java.util.Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    @Named("formatSexe")
    default String formatSexe(Enum<?> sexe) {
        return sexe != null ? sexe.name() : "";
    }

    @Named("formatTypeContrat")
    default String formatTypeContrat(Enum<?> typeContrat) {
        return typeContrat != null ? typeContrat.name() : "";
    }

    @Named("formatStatutAgent")
    default String formatStatutAgent(Enum<?> statutAgent) {
        return statutAgent != null ? statutAgent.name() : "";
    }

    @Named("formatPreferences")
    default String formatPreferences(java.util.List<?> preferences) {
        if (preferences == null || preferences.isEmpty()) return "";
        return preferences.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }
}