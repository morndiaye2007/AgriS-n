package com.agri.sen.services;

import com.agri.sen.model.CultureDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CultureService {
    CultureDTO createCulture(CultureDTO cultureDTO);
    CultureDTO updateCulture(CultureDTO cultureDTO);
    void deleteCulture(Long id);
    CultureDTO getCulture(Long id);
    Page<CultureDTO> getAllCultures(Map<String, String> searchParams, Pageable pageable);
}