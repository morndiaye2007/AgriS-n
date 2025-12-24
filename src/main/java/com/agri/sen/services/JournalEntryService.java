package com.agri.sen.services;

import com.agri.sen.model.JournalEntryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Map;

public interface JournalEntryService {
    JournalEntryDTO createEntry(JournalEntryDTO journalEntryDTO);
    JournalEntryDTO updateEntry(JournalEntryDTO journalEntryDTO);
    void deleteEntry(Long id);
    JournalEntryDTO getEntry(Long id);
    Page<JournalEntryDTO> getAllEntries(Map<String, String> searchParams, Pageable pageable);
    Page<JournalEntryDTO> getEntriesByParcelle(Long parcelleId, Map<String, String> searchParams, Pageable pageable);
    Page<JournalEntryDTO> getCurrentUserEntries(Map<String, String> searchParams, Pageable pageable);
    Double getTotalCost(Long parcelleId, LocalDateTime startDate, LocalDateTime endDate);
}