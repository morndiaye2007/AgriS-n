package com.agri.sen.repository;

import com.agri.sen.entity.ArticleCommandeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommandeRepository extends JpaRepository<ArticleCommandeEntity, Long> {
}
