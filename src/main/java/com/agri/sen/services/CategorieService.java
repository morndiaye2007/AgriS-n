package com.agri.sen.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.agri.sen.model.CategorieDTO;


import java.util.Map;

public interface CategorieService {

    CategorieDTO createCategorie(CategorieDTO categorieDTO);
    CategorieDTO updateCategorie(CategorieDTO categorieDTO);
    void deleteCategorie(Long id);
    CategorieDTO getCategorie(Long id);
    Page<CategorieDTO> getAllCategorie(Map<String, String> searchParams, Pageable pageable);


}
