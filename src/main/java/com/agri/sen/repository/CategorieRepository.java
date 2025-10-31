package com.agri.sen.repository;

import com.agri.sen.entity.CategorieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<CategorieEntity, Long> {
}
