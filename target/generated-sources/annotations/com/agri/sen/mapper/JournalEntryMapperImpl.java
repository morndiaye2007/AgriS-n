package com.agri.sen.mapper;

import com.agri.sen.entity.JournalEntry;
import com.agri.sen.entity.JournalEntry.JournalEntryBuilder;
import com.agri.sen.model.JournalEntryDTO;
import com.agri.sen.model.JournalEntryDTO.JournalEntryDTOBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-24T21:46:57+0000",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class JournalEntryMapperImpl implements JournalEntryMapper {

    @Override
    public JournalEntry asEntity(JournalEntryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        JournalEntryBuilder journalEntry = JournalEntry.builder();

        journalEntry.id( dto.getId() );
        journalEntry.parcelleId( dto.getParcelleId() );
        journalEntry.typeActivite( dto.getTypeActivite() );
        journalEntry.dateActivite( dto.getDateActivite() );
        journalEntry.description( dto.getDescription() );
        journalEntry.quantite( dto.getQuantite() );
        journalEntry.unite( dto.getUnite() );
        journalEntry.cout( dto.getCout() );
        journalEntry.photos( dto.getPhotos() );
        journalEntry.documents( dto.getDocuments() );
        journalEntry.createdAt( dto.getCreatedAt() );
        journalEntry.updatedAt( dto.getUpdatedAt() );

        return journalEntry.build();
    }

    @Override
    public JournalEntryDTO asDto(JournalEntry entity) {
        if ( entity == null ) {
            return null;
        }

        JournalEntryDTOBuilder journalEntryDTO = JournalEntryDTO.builder();

        journalEntryDTO.id( entity.getId() );
        journalEntryDTO.parcelleId( entity.getParcelleId() );
        journalEntryDTO.typeActivite( entity.getTypeActivite() );
        journalEntryDTO.dateActivite( entity.getDateActivite() );
        journalEntryDTO.description( entity.getDescription() );
        journalEntryDTO.quantite( entity.getQuantite() );
        journalEntryDTO.unite( entity.getUnite() );
        journalEntryDTO.cout( entity.getCout() );
        journalEntryDTO.photos( entity.getPhotos() );
        journalEntryDTO.documents( entity.getDocuments() );
        journalEntryDTO.createdAt( entity.getCreatedAt() );
        journalEntryDTO.updatedAt( entity.getUpdatedAt() );

        return journalEntryDTO.build();
    }

    @Override
    public List<JournalEntryDTO> parse(List<JournalEntry> entities) {
        if ( entities == null ) {
            return null;
        }

        List<JournalEntryDTO> list = new ArrayList<JournalEntryDTO>( entities.size() );
        for ( JournalEntry journalEntry : entities ) {
            list.add( asDto( journalEntry ) );
        }

        return list;
    }

    @Override
    public List<JournalEntry> parseToEntity(List<JournalEntryDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<JournalEntry> list = new ArrayList<JournalEntry>( entities.size() );
        for ( JournalEntryDTO journalEntryDTO : entities ) {
            list.add( asEntity( journalEntryDTO ) );
        }

        return list;
    }
}
