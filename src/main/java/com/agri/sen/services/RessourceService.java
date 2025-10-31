package com.agri.sen.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.agri.sen.model.RessourceDTO;


import java.util.Map;

public interface RessourceService {

    RessourceDTO createRessource(RessourceDTO ressourceDTO);
    RessourceDTO updateRessource(RessourceDTO ressourceDTO);
    void deleteRessource(Long id);
    RessourceDTO getRessource(Long id);
    Page<RessourceDTO> getAllRessources(Map<String, String> searchParams, Pageable pageable);


}
