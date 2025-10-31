package com.agri.sen.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.PrintWriter;
import java.util.List;
import com.agri.sen.model.RapportDTO;

import java.util.Map;

public interface RapportService {

    RapportDTO createRapport(RapportDTO rapportDTO);
    RapportDTO updateRapport(RapportDTO rapportDTO);
    void deleteRapport(Long id);
    RapportDTO getRapport(Long id);
    Page<RapportDTO> getAllRapport(Map<String, String> searchParams, Pageable pageable);
    void exportRapport(PrintWriter writer);
    List<RapportDTO> importRapport(List<RapportDTO> rendezVous);

}
