package com.agri.sen.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.agri.sen.model.PaiementDTO;


import java.util.Map;

public interface PaiementService {

    PaiementDTO createPaiement(PaiementDTO paiementDTO);
    PaiementDTO updatePaiement(PaiementDTO paiementDTO);
    void deletePaiement(Long id);
    PaiementDTO getPaiement(Long id);
    Page<PaiementDTO> getAllPaiements(Map<String, String> searchParams, Pageable pageable);


}
