package com.agri.sen.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.agri.sen.model.CommandeDTO;


import java.util.Map;

public interface CommandeService {

    CommandeDTO createCommande(CommandeDTO commandeDTO);
    CommandeDTO updateCommande(CommandeDTO commandeDTO);
    void deleteCommande(Long id);
    CommandeDTO getCommande(Long id);
    Page<CommandeDTO> getAllCommandes(Map<String, String> searchParams, Pageable pageable);


}
