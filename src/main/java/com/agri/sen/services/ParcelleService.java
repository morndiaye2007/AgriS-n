package com.agri.sen.services;

import com.agri.sen.model.ParcelleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ParcelleService {
    ParcelleDTO createParcelle(ParcelleDTO parcelleDTO);
    ParcelleDTO updateParcelle(ParcelleDTO parcelleDTO);
    void deleteParcelle(Long id);
    ParcelleDTO getParcelle(Long id);
    Page<ParcelleDTO> getAllParcelles(Map<String, String> searchParams, Pageable pageable);
    Page<ParcelleDTO> getCurrentUserParcelles(Map<String, String> searchParams, Pageable pageable);
    Double getTotalSuperficie(Long agriculteurId);
}