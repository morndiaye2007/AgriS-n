package com.agri.sen.mapper;

import com.agri.sen.entity.JournalEntry;
import com.agri.sen.model.JournalEntryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface JournalEntryMapper extends EntityMapper<JournalEntryDTO, JournalEntry>{
}
