package com.agri.sen.services;
import org.springframework.data.domain.Page;
import com.agri.sen.model.RecommandationDTO;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface RecommandationService {

    RecommandationDTO createRecommandation(RecommandationDTO recommandationDTO);
    RecommandationDTO updateRecommandation(RecommandationDTO recommandationDTO);
    void deleteRecommandation(Long id);
    RecommandationDTO getRecommandation(Long id);
    Page<RecommandationDTO> getAllRecommandations(Map<String, String> searchParams, Pageable pageable);


}
