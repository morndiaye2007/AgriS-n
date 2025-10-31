package com.agri.sen.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.agri.sen.model.ArticleCommandeDTO;


import java.util.Map;

public interface ArticleCommandeService {

    ArticleCommandeDTO createArticleCommande(ArticleCommandeDTO articleCommandeDTO);
    ArticleCommandeDTO updateArticleCommande(ArticleCommandeDTO articleCommandeDTO);
    void deleteArticleCommande(Long id);
    ArticleCommandeDTO getArticleCommande(Long id);
    Page<ArticleCommandeDTO> getAllArticleCommande(Map<String, String> searchParams, Pageable pageable);


}
