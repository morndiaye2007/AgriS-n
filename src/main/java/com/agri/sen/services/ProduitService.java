package com.agri.sen.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.PrintWriter;
import java.util.List;
import com.agri.sen.model.ProduitDTO;

import java.util.Map;

public interface ProduitService {

    ProduitDTO createProduit(ProduitDTO produitDTO);
    ProduitDTO updateProduit(ProduitDTO produitDTO);
    void deleteProduit(Long id);
    ProduitDTO getProduit(Long id);
    Page<ProduitDTO> getAllProduit(Map<String, String> searchParams, Pageable pageable);


}
