package com.agri.sen.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.agri.sen.model.AnalyseDTO;


import java.util.Map;

public interface AnalyseService {

    AnalyseDTO createAnalyse(AnalyseDTO analyseDTO);
    AnalyseDTO updateAnalyse(AnalyseDTO analyseDTO);
    void deleteAnalyse(Long id);
   AnalyseDTO getAnalyse(Long id);
   AnalyseDTO getAllAnalyses(Map<String, String> searchParams, Pageable pageable);

}
