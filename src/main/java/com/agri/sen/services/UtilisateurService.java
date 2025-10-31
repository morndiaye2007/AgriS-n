package com.agri.sen.services;
import org.springframework.data.domain.Page;
import com.agri.sen.model.UtilisateurDTO;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UtilisateurService {

    UtilisateurDTO createUtilisateur(UtilisateurDTO utilisateurDTO);
    UtilisateurDTO updateUtilisateur(UtilisateurDTO utilisateurDTO);
    void deleteUtilisateur(Long id);
    UtilisateurDTO getUtilisateur(Long id);
    Page<UtilisateurDTO> getAllUtilisateurs(Map<String, String> searchParams, Pageable pageable);


}
