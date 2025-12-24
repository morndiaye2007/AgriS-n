package com.agri.sen.services;

import com.agri.sen.model.MeteoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MeteoService {
    MeteoDTO getCurrentMeteo(Double latitude, Double longitude);
    List<MeteoDTO> getMeteoForecast(Double latitude, Double longitude, Integer days);
    MeteoDTO getMeteo(Long id);
    Page<MeteoDTO> getAllMeteo(Map<String, String> searchParams, Pageable pageable);
    List<MeteoDTO> getMeteoByVille(String ville);
}